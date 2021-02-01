package com.otta.cooperative.poll.meeting.extractor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.otta.cooperative.poll.meeting.entity.MeetingEntity;
import com.otta.cooperative.poll.meeting.entity.PollEntity;

@ExtendWith(MockitoExtension.class)
public class PollEntityExtractorTest {
    @InjectMocks
    private PollEntityExtractor extractor;

    @Mock
    private MeetingEntity meetingEntity;
    @Mock
    private PollEntity pollEntity;

    @Test
    public void shouldCorrectlyExtractPollEntity() {
        // given
        given(meetingEntity.getPoll()).willReturn(pollEntity);
        // when
        Optional<PollEntity> actualValue = extractor.extract(Optional.of(meetingEntity));
        // then
        assertEquals(pollEntity, actualValue.get());
    }

    @Test
    public void shouldCorrectlyExtractEmptyOptionalWhenMeetingEntityDontHaveAPollEntity() {
        // given
        // when
        Optional<PollEntity> actualValue = extractor.extract(Optional.of(meetingEntity));
        // then
        assertFalse(actualValue.isPresent());
    }

    @Test
    public void shouldCorrectlyExtractEmptyOptionalWhenOptionalMeetingEntityIsEmpty() {
        // given
        // when
        Optional<PollEntity> actualValue = extractor.extract(Optional.empty());
        // then
        assertFalse(actualValue.isPresent());
    }

}
