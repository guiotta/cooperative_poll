package com.otta.cooperative.poll.meeting.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.otta.cooperative.poll.meeting.entity.MeetingEntity;
import com.otta.cooperative.poll.meeting.entity.PollEntity;
import com.otta.cooperative.poll.meeting.model.poll.PollOutput;

@ExtendWith(MockitoExtension.class)
public class PollOutputMapperTest {
    private static final Long ID = 10l;
    private static final Long MEETING_ID = 15l;

    @InjectMocks
    private PollOutputMapper mapper;

    @Mock
    private PollEntity pollEntity;
    @Mock
    private MeetingEntity meetingEntity;

    private LocalDateTime open;
    private LocalDateTime close;

    @BeforeEach
    protected void setUp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        open = LocalDateTime.parse("2021-01-31 12:00:00", formatter);
        close = LocalDateTime.parse("2021-01-31 13:00:00", formatter);

        given(pollEntity.getId()).willReturn(ID);
        given(pollEntity.getMeeting()).willReturn(meetingEntity);
        given(pollEntity.getOpen()).willReturn(open);
        given(pollEntity.getClose()).willReturn(close);
        given(meetingEntity.getId()).willReturn(MEETING_ID);
    }

    @Test
    public void shouldCorrectlyMapPollOutput() {
        // given
        // when
        PollOutput actualValue = mapper.map(pollEntity);
        // then
        assertEquals(ID, actualValue.getId());
        assertEquals(MEETING_ID, actualValue.getMeetingId());
        assertEquals(open, actualValue.getOpenPollDateTime());
        assertEquals(close, actualValue.getClosePollDateTime());
    }

}
