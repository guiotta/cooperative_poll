package com.otta.cooperative.poll.meeting.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quartz.SchedulerException;

import com.otta.cooperative.poll.meeting.entity.MeetingEntity;
import com.otta.cooperative.poll.meeting.entity.PollEntity;
import com.otta.cooperative.poll.meeting.extractor.PollEntityExtractor;
import com.otta.cooperative.poll.meeting.job.PollEndJobScheduler;
import com.otta.cooperative.poll.meeting.mapper.MeetingEntityMapper;
import com.otta.cooperative.poll.meeting.mapper.MeetingOutputMapper;
import com.otta.cooperative.poll.meeting.mapper.PollEntityMapper;
import com.otta.cooperative.poll.meeting.mapper.PollOutputMapper;
import com.otta.cooperative.poll.meeting.model.MeetingInput;
import com.otta.cooperative.poll.meeting.model.MeetingOutput;
import com.otta.cooperative.poll.meeting.model.poll.PollInput;
import com.otta.cooperative.poll.meeting.model.poll.PollOutput;
import com.otta.cooperative.poll.meeting.model.result.ResultOutput;
import com.otta.cooperative.poll.meeting.repository.MeetingRepository;
import com.otta.cooperative.poll.meeting.result.mapper.ResultOutputMapper;
import com.otta.cooperative.poll.vote.entity.VoteOptionEntity;
import com.otta.cooperative.poll.vote.repository.VoteOptionRepository;

@ExtendWith(MockitoExtension.class)
public class MeetingServiceTest {
    private static final Long MEETING_ID = 10l;
    private static final Long POLL_ID = 20l;
    private static final String ALL = "all";
    private static final String OPEN = "open";
    private static final String UNKNOWN = "unknown";

    private MeetingService meetingService;

    @Mock
    private MeetingRepository meetingRepository;
    @Mock
    private MeetingEntityMapper meetingEntityMapper;
    @Mock
    private MeetingOutputMapper meetingOutputMapper;
    @Mock
    private PollEntityMapper pollEntityMapper;
    @Mock
    private PollOutputMapper pollOutputMapper;
    @Mock
    private PollEndJobScheduler pollEndJobScheduler;
    @Mock
    private VoteOptionRepository voteOptionRepository;
    @Mock
    private PollEntityExtractor pollEntityExtractor;
    @Mock
    private ResultOutputMapper resultOutputMapper;

    @Mock
    private MeetingInput meeetingInput;
    @Mock
    private MeetingEntity meetingEntity;
    @Mock
    private MeetingEntity meetingEntitySaved;
    @Mock
    private MeetingOutput meetingOutput;
    @Mock
    private PollInput pollInput;
    @Mock
    private PollEntity pollEntity;
    @Mock
    private PollOutput pollOutput;
    @Mock
    private VoteOptionEntity voteOptionEntity;
    @Mock
    private ResultOutput resultOutput;

    private LocalDateTime closeDate;
    @BeforeEach
    protected void setUp() {
        meetingService = spy(new MeetingService(meetingRepository, meetingEntityMapper, meetingOutputMapper,
                pollEntityMapper, pollOutputMapper, pollEndJobScheduler, voteOptionRepository, pollEntityExtractor,
                resultOutputMapper));
        closeDate = LocalDateTime.now();
    }

    @Test
    public void shouldCorrectlySaveMeeting() {
        // given
        given(meetingEntityMapper.map(meeetingInput)).willReturn(meetingEntity);
        given(meetingRepository.save(meetingEntity)).willReturn(meetingEntitySaved);
        given(meetingOutputMapper.map(meetingEntitySaved)).willReturn(meetingOutput);
        // when
        MeetingOutput actualValue = meetingService.saveMeeting(meeetingInput);
        // then
        assertEquals(meetingOutput, actualValue);
    }

    @Test
    public void shouldCorrectlyFindAllMeetings() {
        // given
        given(meetingRepository.findAll()).willReturn(Lists.list(meetingEntity));
        given(meetingOutputMapper.map(meetingEntity)).willReturn(meetingOutput);
        // when
        Collection<MeetingOutput> actualValue = meetingService.findAll(ALL);
        // then
        assertThat(actualValue, Matchers.containsInAnyOrder(meetingOutput));
    }

    @Test
    public void shouldCorrectlyFindOnlyeMeetingsWithOpenPoll() {
        // given
        given(meetingRepository.findByPollCloseAfterNow()).willReturn(Lists.list(meetingEntity));
        given(meetingOutputMapper.map(meetingEntity)).willReturn(meetingOutput);
        // when
        Collection<MeetingOutput> actualValue = meetingService.findAll(OPEN);
        // then
        assertThat(actualValue, Matchers.containsInAnyOrder(meetingOutput));
    }

    @Test
    public void shouldNotExecuteRepositoryWhenSerachTypeIsUnknown() {
        // given
        // when
        Collection<MeetingOutput> actualValue = meetingService.findAll(UNKNOWN);
        // then
        assertTrue(actualValue.isEmpty());
        verify(meetingRepository, never()).findAll();
        verify(meetingRepository, never()).findByPollCloseAfterNow();
    }

    @Test
    public void shouldCorrectlySavePoll() throws SchedulerException {
        // given
        given(pollInput.getMeetingId()).willReturn(MEETING_ID);
        given(meetingRepository.findById(MEETING_ID)).willReturn(Optional.of(meetingEntity));
        given(pollEntityExtractor.extract(Optional.of(meetingEntity))).willReturn(Optional.empty());
        given(pollEntityMapper.map(pollInput, meetingEntity)).willReturn(pollEntity);
        given(meetingRepository.save(meetingEntity)).willReturn(meetingEntitySaved);
        given(meetingEntitySaved.getPoll()).willReturn(pollEntity);
        given(pollOutputMapper.map(pollEntity)).willReturn(pollOutput);
        // when
        PollOutput actualValue = meetingService.savePoll(pollInput);
        // then
        verify(meetingEntity).setPoll(pollEntity);
        assertEquals(pollOutput, actualValue);
    }

    @Test
    public void shouldSchedulePollEndJob() throws SchedulerException {
        // given
        given(pollInput.getMeetingId()).willReturn(MEETING_ID);
        given(meetingRepository.findById(MEETING_ID)).willReturn(Optional.of(meetingEntity));
        given(pollEntityExtractor.extract(Optional.of(meetingEntity))).willReturn(Optional.empty());
        given(pollEntityMapper.map(pollInput, meetingEntity)).willReturn(pollEntity);
        given(meetingRepository.save(meetingEntity)).willReturn(meetingEntitySaved);
        given(meetingEntitySaved.getPoll()).willReturn(pollEntity);
        given(pollOutputMapper.map(pollEntity)).willReturn(pollOutput);
        given(pollEntity.getClose()).willReturn(closeDate);
        given(pollEntity.getId()).willReturn(POLL_ID);
        // when
        meetingService.savePoll(pollInput);
        // then
        verify(pollEndJobScheduler).scheduleJob(closeDate, POLL_ID);
    }

    @Test
    public void shouldThrowAnExceptionWhenSavingAPollAndMeetingAlreadyHaveAPollSaved() throws SchedulerException {
        // given
        given(pollInput.getMeetingId()).willReturn(MEETING_ID);
        given(meetingRepository.findById(MEETING_ID)).willReturn(Optional.of(meetingEntity));
        given(pollEntityExtractor.extract(Optional.of(meetingEntity))).willReturn(Optional.of(pollEntity));
        // when
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            meetingService.savePoll(pollInput);
        });
        // then
    }

    @Test
    public void shouldCorrectlyGenerateResult() {
        // given
        when(meetingService.getLocalDateTimeNow()).thenReturn(LocalDateTime.MAX);
        when(meetingRepository.findById(MEETING_ID)).thenReturn(Optional.of(meetingEntity));
        when(voteOptionRepository.findAll()).thenReturn(Lists.list(voteOptionEntity));
        when(pollEntityExtractor.extract(Optional.of(meetingEntity))).thenReturn(Optional.of(pollEntity));
        when(pollEntity.getClose()).thenReturn(closeDate);
        when(resultOutputMapper.map(MEETING_ID, pollEntity, Lists.list(voteOptionEntity))).thenReturn(resultOutput);
        // when
        ResultOutput actualValue = meetingService.generateResult(MEETING_ID);
        // then
        assertEquals(resultOutput, actualValue);
    }

    @Test
    public void shouldThrowExceptionWhenGeneratingResultsForAOpenPoll() {
        // given
        when(meetingService.getLocalDateTimeNow()).thenReturn(LocalDateTime.MIN);
        when(meetingRepository.findById(MEETING_ID)).thenReturn(Optional.of(meetingEntity));
        when(voteOptionRepository.findAll()).thenReturn(Lists.list(voteOptionEntity));
        when(pollEntityExtractor.extract(Optional.of(meetingEntity))).thenReturn(Optional.of(pollEntity));
        when(pollEntity.getClose()).thenReturn(closeDate);
        // when
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            meetingService.generateResult(MEETING_ID);
        });
        // then
    }

    @Test
    public void shouldThrowAExceptionWhenGeneratingAResultForAMeetingWithoutPoll() {
        // given
        given(meetingRepository.findById(MEETING_ID)).willReturn(Optional.of(meetingEntity));
        given(voteOptionRepository.findAll()).willReturn(Lists.list(voteOptionEntity));
        given(pollEntityExtractor.extract(Optional.of(meetingEntity))).willReturn(Optional.empty());
        // when
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            meetingService.generateResult(MEETING_ID);
        });
        // then
    }

}
