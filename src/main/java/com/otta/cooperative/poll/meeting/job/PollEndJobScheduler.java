package com.otta.cooperative.poll.meeting.job;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.otta.cooperative.poll.meeting.repository.PollRepository;

@Component
public class PollEndJobScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(PollEndJobScheduler.class);

    
    private final PollRepository pollRepository;
    private final SchedulerFactory factory;
    private final JobDetailFactory jobDetailFactory;
    private final QuartzTriggerFactory quartzTriggerFactory;

    public PollEndJobScheduler(PollRepository pollRepository, SchedulerFactory factory,
            JobDetailFactory jobDetailFactory, QuartzTriggerFactory quartzTriggerFactory) {
        this.pollRepository = pollRepository;
        this.factory = factory;
        this.jobDetailFactory = jobDetailFactory;
        this.quartzTriggerFactory = quartzTriggerFactory;
    }

    public Date scheduleJob(LocalDateTime executeDateTime, Long pollId) throws SchedulerException {
        Timestamp timestamp = this.convertLocalDateTimeToTimestamp(executeDateTime);
        Scheduler scheduler = factory.getScheduler();
        Trigger trigger = quartzTriggerFactory.create(timestamp);
        JobDetail job = jobDetailFactory.create();

        LOGGER.info("Scheduling PollEndJob for Poll with Id {} to execute in timestamp {}.", pollId, timestamp);

        scheduler.start();
        job.getJobDataMap().put("pollId", pollId);
        job.getJobDataMap().put("pollRepository", pollRepository);
        return scheduler.scheduleJob(job, trigger);
    }

    protected Timestamp convertLocalDateTimeToTimestamp(LocalDateTime executeDateTime) {
        return Timestamp.valueOf(executeDateTime);
    }
}
