package com.otta.cooperative.poll.meeting.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.Collection;

import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quartz.SchedulerException;
import org.springframework.http.ResponseEntity;

import com.otta.cooperative.poll.meeting.model.MeetingInput;
import com.otta.cooperative.poll.meeting.model.MeetingOutput;
import com.otta.cooperative.poll.meeting.model.poll.PollInput;
import com.otta.cooperative.poll.meeting.model.poll.PollOutput;
import com.otta.cooperative.poll.meeting.model.result.ResultOutput;
import com.otta.cooperative.poll.meeting.service.MeetingService;

@ExtendWith(MockitoExtension.class)
public class MeetingControllerTest {
    private static final String PARAMETER = "ALL";
    private static final Long MEETING_ID = 1l;

    @InjectMocks
    private MeetingController controller;

    @Mock
    private MeetingService meetingService;

    @Mock
    private MeetingInput meetingInput;
    @Mock
    private MeetingOutput meetingOutput;
    @Mock
    private PollInput pollInput;
    @Mock
    private PollOutput pollOutput;
    @Mock
    private ResultOutput resultOutput;

    @Test
    public void shouldCorrectySaveMeeting() {
        // given
        given(meetingService.saveMeeting(meetingInput)).willReturn(meetingOutput);
        // when
        ResponseEntity<MeetingOutput> actualValue = controller.saveMeeting(meetingInput);
        // then
        assertEquals(meetingOutput, actualValue.getBody());
    }

    @Test
    public void shouldCorrectlyFindAllMeetings() {
        // given
        given(meetingService.findAll(Mockito.anyString())).willReturn(Lists.list(meetingOutput));
        // when
        ResponseEntity<Collection<MeetingOutput>> actualValue = controller.findAllMeetings(PARAMETER);
        // then
        assertThat(actualValue.getBody(), Matchers.containsInAnyOrder(meetingOutput));
    }

    @Test
    public void shouldCorrectlySavePoll() throws SchedulerException {
        // given
        given(meetingService.savePoll(pollInput)).willReturn(pollOutput);
        // when
        ResponseEntity<PollOutput> actualValue = controller.savePoll(pollInput);
        // then
        assertEquals(pollOutput, actualValue.getBody());
    }

    @Test
    public void shouldCorrectlyGetPollResult() throws Exception {
        // given
        given(meetingService.generateResult(MEETING_ID)).willReturn(resultOutput);
        // when
        ResponseEntity<ResultOutput> actualValue = controller.pollResult(MEETING_ID);
        // then
        assertEquals(resultOutput, actualValue.getBody());
    }

}
