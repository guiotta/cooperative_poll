package com.otta.cooperative.poll.meeting.listener;

import java.util.Collection;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.otta.cooperative.poll.meeting.entity.PollEntity;
import com.otta.cooperative.poll.meeting.job.PollEndJobScheduler;
import com.otta.cooperative.poll.meeting.repository.PollRepository;

@Component
public class ContextStartupEndedEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContextStartupEndedEventListener.class);

    private final PollRepository pollRepository;
    private final PollEndJobScheduler pollEndJobScheduler;

    public ContextStartupEndedEventListener(PollRepository pollRepository, PollEndJobScheduler pollEndJobScheduler) {
        this.pollRepository = pollRepository;
        this.pollEndJobScheduler = pollEndJobScheduler;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Collection<PollEntity> polls = pollRepository.findAllByEnabledTrue();
        LOGGER.debug("PollEntities in database with enabled false: {}.", polls);

        polls.stream().forEach(entity -> {
            try {
                pollEndJobScheduler.scheduleJob(entity.getClose(), entity.getId());
            } catch (SchedulerException e) {
                LOGGER.error("An exception was been throw when scheduler is processing PollId {}.", entity.getId(), e);
            }
        });
    }
}
