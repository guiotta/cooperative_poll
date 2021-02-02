package com.otta.cooperative.poll.meeting.extractor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;

@ExtendWith(MockitoExtension.class)
public class PollIdQuartzContextoExtractorTest {
    private static final Long POLL_ID = 10l;
    private static final String POLL_ID_KEY = "pollId";

    @InjectMocks
    private PollIdQuartzContextoExtractor extractor;

    @Mock
    private JobExecutionContext context;
    @Mock
    private JobDetail jobDetail;
    @Mock
    private JobDataMap jobDataMap;

    @Test
    public void shouldCorrectlyExtractPollId() {
        // given
        given(context.getJobDetail()).willReturn(jobDetail);
        given(jobDetail.getJobDataMap()).willReturn(jobDataMap);
        given(jobDataMap.getLong(POLL_ID_KEY)).willReturn(POLL_ID);
        // when
        Optional<Long> actualValue = extractor.extract(context);
        // then
        assertEquals(POLL_ID, actualValue.get());
    }

    @Test
    public void shouldReturnEmptyOptionalWhenContextDontHaveAJobDetail() {
        // given
        given(context.getJobDetail()).willReturn(null);
        // when
        Optional<Long> actualValue = extractor.extract(context);
        // then
        assertEquals(Optional.empty(), actualValue);
    }

    @Test
    public void shouldReturnEmptyOptionalWhenJobDetailDontHaveAJobDetailMap() {
        // given
        given(context.getJobDetail()).willReturn(jobDetail);
        given(jobDetail.getJobDataMap()).willReturn(null);
        // when
        Optional<Long> actualValue = extractor.extract(context);
        // then
        assertEquals(Optional.empty(), actualValue);
    }
}
