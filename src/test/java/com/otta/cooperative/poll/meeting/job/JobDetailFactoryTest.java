package com.otta.cooperative.poll.meeting.job;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quartz.JobDetail;

@ExtendWith(MockitoExtension.class)
public class JobDetailFactoryTest {
    @InjectMocks
    private JobDetailFactory factory;

    @Test
    public void shouldCorrectlyCreateJobDetail() {
        // given
        // when
        JobDetail jobDetail = factory.create();
        // then
        assertEquals(PollEndJob.class, jobDetail.getJobClass());
    }

    @Test
    public void shouldCreateNotDurableJobDetail() {
     // given
        // when
        JobDetail jobDetail = factory.create();
        // then
        assertFalse(jobDetail.isDurable());
    }

}
