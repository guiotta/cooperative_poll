package com.otta.cooperative.poll.meeting.job;

import java.util.Optional;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.otta.cooperative.poll.meeting.entity.PollEntity;
import com.otta.cooperative.poll.meeting.extractor.PollIdQuartzContextoExtractor;
import com.otta.cooperative.poll.meeting.repository.PollRepository;

@Component
public class PollEndJob implements Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(PollEndJob.class);

    private final PollRepository pollRepository;
    private final PollIdQuartzContextoExtractor pollIdQuartzContextoExtractor;

    public PollEndJob(PollRepository pollRepository, PollIdQuartzContextoExtractor pollIdQuartzContextoExtractor) {
        this.pollRepository = pollRepository;
        this.pollIdQuartzContextoExtractor = pollIdQuartzContextoExtractor;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Optional<Long> optionalPollId = pollIdQuartzContextoExtractor.extract(context);

        if (optionalPollId.isPresent()) {
            Long pollId = optionalPollId.get();
            Optional<PollEntity> optionalEntity = pollRepository.findById(pollId);

            if (optionalEntity.isPresent()) {
                PollEntity entity = optionalEntity.get();
                entity.setEnabled(false);

                LOGGER.info("Updating PollEntity with id {} to disabled.", pollId);
                pollRepository.save(entity);
            } else {
                LOGGER.error("Could not find any PollEntity with Id {}.", pollId);
            }
        } else {
            LOGGER.error("Could not extract any PollId from JobExecutionContext to finalize Poll.");
        }
    }
}
