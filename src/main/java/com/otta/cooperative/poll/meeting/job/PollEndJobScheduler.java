package com.otta.cooperative.poll.meeting.job;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Component;

import com.otta.cooperative.poll.meeting.repository.PollRepository;

@Component
public class PollEndJobScheduler {
    private final PollRepository pollRepository;
    private final SchedulerFactory factory;
    private final JobDetailFactory jobDetailFactory;
    private final TriggerBuilder<Trigger> triggerBuilder;

    public PollEndJobScheduler(PollRepository pollRepository, SchedulerFactory factory,
            JobDetailFactory jobDetailFactory, TriggerBuilder<Trigger> triggerBuilder) {
        this.pollRepository = pollRepository;
        this.factory = factory;
        this.jobDetailFactory = jobDetailFactory;
        this.triggerBuilder = triggerBuilder;
    }

    public Date scheduleJob(LocalDateTime executeDateTime, Long pollId) throws SchedulerException {
        Timestamp timestamp = this.convertLocalDateTimeToTimestamp(executeDateTime);
        Scheduler scheduler = factory.getScheduler();
        scheduler.start();
        JobDetail job = jobDetailFactory.create();
        job.getJobDataMap().put("pollId", pollId);
        job.getJobDataMap().put("pollRepository", pollRepository);
        Trigger trigger = triggerBuilder
                .startAt(timestamp)
                .build();
        return scheduler.scheduleJob(job, trigger);
    }

    protected Timestamp convertLocalDateTimeToTimestamp(LocalDateTime executeDateTime) {
        return Timestamp.valueOf(executeDateTime);
    }
}
