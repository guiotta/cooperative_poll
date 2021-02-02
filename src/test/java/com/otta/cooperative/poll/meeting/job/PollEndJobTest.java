package com.otta.cooperative.poll.meeting.job;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.otta.cooperative.poll.meeting.entity.PollEntity;
import com.otta.cooperative.poll.meeting.extractor.PollIdQuartzContextoExtractor;
import com.otta.cooperative.poll.meeting.repository.PollRepository;

@ExtendWith(MockitoExtension.class)
public class PollEndJobTest {
    private static final Long POLL_ID = 10l;

    @InjectMocks
    private PollEndJob pollEndJob;

    @Mock
    private PollRepository pollRepository;
    @Mock
    private PollIdQuartzContextoExtractor pollIdQuartzContextoExtractor;

    @Mock
    private JobExecutionContext context;
    @Mock
    private PollEntity pollEntity;

    @BeforeEach
    protected void setUp() throws Exception {
    }

    @Test
    public void shouldCorrectlyUpdatePollEntityEnabledProperty() throws JobExecutionException {
        // given
        given(pollIdQuartzContextoExtractor.extract(context)).willReturn(Optional.of(POLL_ID));
        given(pollRepository.findById(POLL_ID)).willReturn(Optional.of(pollEntity));
        // when
        pollEndJob.execute(context);
        // then
        verify(pollEntity).setEnabled(Boolean.FALSE);
        verify(pollRepository).save(pollEntity);
    }

    @Test
    public void shouldDoNothingWhenOptionalPollIdIsEmpty() throws JobExecutionException {
        // given
        given(pollIdQuartzContextoExtractor.extract(context)).willReturn(Optional.empty());
        // when
        pollEndJob.execute(context);
        // then
        verify(pollEntity, never()).setEnabled(Boolean.FALSE);
        verify(pollRepository, never()).save(pollEntity);
    }

    @Test
    public void shouldDoNothingWhenCouldNotFindAPollEntityForPollId() throws JobExecutionException {
        // given
        given(pollIdQuartzContextoExtractor.extract(context)).willReturn(Optional.of(POLL_ID));
        given(pollRepository.findById(POLL_ID)).willReturn(Optional.empty());
        // when
        pollEndJob.execute(context);
        // then
        verify(pollEntity, never()).setEnabled(Boolean.FALSE);
        verify(pollRepository, never()).save(pollEntity);
    }
}
