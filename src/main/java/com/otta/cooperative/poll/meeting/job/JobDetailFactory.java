package com.otta.cooperative.poll.meeting.job;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.springframework.stereotype.Component;

@Component
public class JobDetailFactory {

    public JobDetail create() {
        return JobBuilder.newJob(PollEndJob.class).build();
    }
}
