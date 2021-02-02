package com.otta.cooperative.poll.meeting.job;

import java.sql.Timestamp;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Component;

@Component
public class QuartzTriggerFactory {

    public Trigger create(Timestamp timestamp, JobDetail jobDetail) {
        return TriggerBuilder
                .newTrigger()
                .forJob(jobDetail)
                .startAt(timestamp)
                .build();
    }
}
