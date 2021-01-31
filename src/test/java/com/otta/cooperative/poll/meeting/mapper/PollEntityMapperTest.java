package com.otta.cooperative.poll.meeting.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.otta.cooperative.poll.meeting.entity.MeetingEntity;
import com.otta.cooperative.poll.meeting.entity.PollEntity;
import com.otta.cooperative.poll.meeting.model.poll.PollInput;

@ExtendWith(MockitoExtension.class)
public class PollEntityMapperTest {
    private static final Long SECONDS_TO_ADD = 100l;

    @Spy
    private PollEntityMapper mapper;

    @Mock
    private PollInput pollInput;
    @Mock
    private MeetingEntity meetingEntity;

    DateTimeFormatter formatter;
    private LocalDateTime dateTimeNow;
    private LocalDateTime dateTimePlus;

    @BeforeEach
    protected void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dateTimeNow = LocalDateTime.parse("2021-01-31 12:00:00", formatter);
        dateTimePlus = LocalDateTime.parse("2021-01-31 13:00:00", formatter);
    }

    @Test
    public void shouldCorrectlyMapPollEntity() {
        // given
        when(pollInput.getTimeToEndInSeconds()).thenReturn(SECONDS_TO_ADD);
        when(mapper.getLocalDateTimeNow()).thenReturn(dateTimeNow);
        when(mapper.addSecondsToLocalDateTime(dateTimeNow, SECONDS_TO_ADD)).thenReturn(dateTimePlus);
        // when
        PollEntity actualValue = mapper.map(pollInput, meetingEntity);
        // then
        assertEquals(dateTimeNow, actualValue.getOpen());
        assertEquals(dateTimePlus, actualValue.getClose());
        assertEquals(meetingEntity, actualValue.getMeeting());
        assertTrue(actualValue.getEnabled());
    }

    @Test
    public void shouldCorrectlyAddSecondsToLocalDateTime() {
        // given
        String startDate = "2021-01-31 12:00:00";
        String plusDate = "2021-01-31 12:01:40";

        LocalDateTime startValue = LocalDateTime.parse(startDate, formatter);
        LocalDateTime plusValue = LocalDateTime.parse(plusDate, formatter);
        // when
        LocalDateTime actualValue = mapper.addSecondsToLocalDateTime(startValue, SECONDS_TO_ADD);
        // then
        assertEquals(plusValue, actualValue);

    }

}
