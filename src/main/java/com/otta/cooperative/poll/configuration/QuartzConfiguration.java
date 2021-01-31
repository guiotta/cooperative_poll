package com.otta.cooperative.poll.configuration;

import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfiguration {

    @Bean
    public SchedulerFactory schedulerFactory() {
        return new StdSchedulerFactory();
    }

}
