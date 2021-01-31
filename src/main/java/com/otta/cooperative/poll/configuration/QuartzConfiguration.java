package com.otta.cooperative.poll.configuration;

import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

import com.otta.cooperative.poll.meeting.job.PollEndJob;

//@Configuration
public class QuartzConfiguration {

    @Bean
    public JobDetailFactoryBean jobDetail() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(PollEndJob.class);
        jobDetailFactory.setDescription("Invoke Poll End Job service...");
        jobDetailFactory.setDurability(false);
        return jobDetailFactory;
    }

    @Bean
    @Scope("prototype")
    public Trigger trigger(JobDetail job) {
        return TriggerBuilder.newTrigger().forJob(job)
          .withIdentity("Qrtz_Trigger")
          .withDescription("Poll End Job trigger")
          //.withSchedule(SimpleScheduleBuilder().repeatForever().withIntervalInHours(1))
          .build();
    }
}
