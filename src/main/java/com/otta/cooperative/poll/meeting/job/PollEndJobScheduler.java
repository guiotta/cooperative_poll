package com.otta.cooperative.poll.meeting.job;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.otta.cooperative.poll.meeting.repository.PollRepository;

@Component
public class PollEndJobScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(PollEndJobScheduler.class);

    private final PollRepository pollRepository;
    private final JobDetailFactory jobDetailFactory;
    private final QuartzTriggerFactory quartzTriggerFactory;
    private final Scheduler scheduler;

    public PollEndJobScheduler(PollRepository pollRepository, JobDetailFactory jobDetailFactory,
            QuartzTriggerFactory quartzTriggerFactory, Scheduler scheduler) {
        this.pollRepository = pollRepository;
        this.jobDetailFactory = jobDetailFactory;
        this.quartzTriggerFactory = quartzTriggerFactory;
        this.scheduler = scheduler;
    }

    public Date scheduleJob(LocalDateTime executeDateTime, Long pollId) throws SchedulerException {
        Timestamp timestamp = this.convertLocalDateTimeToTimestamp(executeDateTime);
        JobDetail job = jobDetailFactory.create();
        Trigger trigger = quartzTriggerFactory.create(timestamp, job);

        LOGGER.info("Scheduling PollEndJob for Poll with Id {} to execute in timestamp {}.", pollId, timestamp);

        job.getJobDataMap().put("pollId", pollId);
        job.getJobDataMap().put("pollRepository", pollRepository);
        return scheduler.scheduleJob(job, trigger);
    }

    protected Timestamp convertLocalDateTimeToTimestamp(LocalDateTime executeDateTime) {
        return Timestamp.valueOf(executeDateTime);
    }
}
