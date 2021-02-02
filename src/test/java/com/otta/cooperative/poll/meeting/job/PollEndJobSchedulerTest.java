package com.otta.cooperative.poll.meeting.job;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

@ExtendWith(MockitoExtension.class)
public class PollEndJobSchedulerTest {
    private static final Long POLL_ID = 15l;

    private PollEndJobScheduler scheduler;

    @Mock
    private JobDetailFactory jobDetailFactory;
    @Mock
    private QuartzTriggerFactory quartzTriggerFactory;
    @Mock
    private Scheduler quartzScheduler;

    @Mock
    private Timestamp timestamp;
    @Mock
    private JobDetail jobDetail;
    @Mock
    private JobDataMap jobDataMap;
    @Mock
    private Trigger trigger;
    @Mock
    private Date date;

    private LocalDateTime dateTime;

    @BeforeEach
    protected void setUp() throws SchedulerException {
        this.scheduler = spy(new PollEndJobScheduler(jobDetailFactory, quartzTriggerFactory, quartzScheduler));
        this.dateTime = LocalDateTime.now();

        when(scheduler.convertLocalDateTimeToTimestamp(dateTime)).thenReturn(timestamp);
        when(jobDetailFactory.create()).thenReturn(jobDetail);
        when(jobDetail.getJobDataMap()).thenReturn(jobDataMap);
        when(quartzTriggerFactory.create(timestamp, jobDetail)).thenReturn(trigger);
        when(quartzScheduler.scheduleJob(jobDetail, trigger)).thenReturn(date);
    }

    @Test
    public void shouldCorrectlySchedulePollEndJob() throws Exception {
        // given
        // when
        Date actualValue = scheduler.scheduleJob(dateTime, POLL_ID);
        // then
        assertEquals(date, actualValue);
    }

}
