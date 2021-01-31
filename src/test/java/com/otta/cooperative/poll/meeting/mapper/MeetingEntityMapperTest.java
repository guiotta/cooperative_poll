package com.otta.cooperative.poll.meeting.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.otta.cooperative.poll.meeting.entity.MeetingEntity;
import com.otta.cooperative.poll.meeting.model.MeetingInput;

@ExtendWith(MockitoExtension.class)
public class MeetingEntityMapperTest {
    private static final String SUBJECT = "subject";

    @InjectMocks
    private MeetingEntityMapper mapper;

    @Mock
    private MeetingInput input;

    @BeforeEach
    protected void setUp() {
        given(input.getSubject()).willReturn(SUBJECT);
    }

    @Test
    public void shouldCorrectlyMapMeetingEntity() {
        // given
        // when
        MeetingEntity actualValue = mapper.map(input);
        // then
        assertEquals(SUBJECT, actualValue.getSubject());
        assertNull(actualValue.getPoll());
    }

}
