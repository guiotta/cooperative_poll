package com.otta.cooperative.poll.meeting.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.otta.cooperative.poll.meeting.entity.MeetingEntity;
import com.otta.cooperative.poll.meeting.model.MeetingOutput;

@ExtendWith(MockitoExtension.class)
public class MeetingOutputMapperTest {
    private static final String SUBJECT = "subject";
    private static final Long ID = 100l;

    @InjectMocks
    private MeetingOutputMapper mapper;

    @Mock
    private MeetingEntity entity;

    @BeforeEach
    protected void setUp() {
        given(entity.getId()).willReturn(ID);
        given(entity.getSubject()).willReturn(SUBJECT);
    }

    @Test
    public void shouldCorrectlyMapMeetingOutput() {
        // given
        // when
        MeetingOutput actualValue = mapper.map(entity);
        // then
        assertEquals(ID, actualValue.getId());
        assertEquals(SUBJECT, actualValue.getSubject());
    }

}
