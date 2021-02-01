package com.otta.cooperative.poll.meeting.result.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.otta.cooperative.poll.meeting.entity.PollEntity;
import com.otta.cooperative.poll.meeting.model.result.ResultOutput;
import com.otta.cooperative.poll.meeting.model.result.VoteOptionResultOutput;
import com.otta.cooperative.poll.vote.entity.VoteOptionEntity;

@ExtendWith(MockitoExtension.class)
public class ResultOutputMapperTest {
    private static final Long MEETING_ID = 50l;

    @InjectMocks
    private ResultOutputMapper mapper;

    @Mock
    private VoteOptionResultOutputCollectionMapper voteOptionResultOutputCollectionMapper;

    @Mock
    private Collection<VoteOptionResultOutput> voteOptionResultOutputs;
    @Mock
    private PollEntity pollEntity;
    @Mock
    private Collection<VoteOptionEntity> voteOptions;
    private LocalDateTime endDateTime = LocalDateTime.now();

    @BeforeEach
    protected void setUp() {
        given(voteOptionResultOutputCollectionMapper.map(pollEntity, voteOptions)).willReturn(voteOptionResultOutputs);
        given(pollEntity.getEnabled()).willReturn(Boolean.TRUE);
        given(pollEntity.getClose()).willReturn(endDateTime);
    }

    @Test
    public void shouldCorrectlyMapResultOutput() {
        // given
        // when
        ResultOutput actualValue = mapper.map(MEETING_ID, pollEntity, voteOptions);
        // then
        assertEquals(MEETING_ID, actualValue.getMeetingId());
        assertEquals(voteOptionResultOutputs, actualValue.getVotes());
        assertEquals(endDateTime, actualValue.getCloseDate());
        assertTrue(actualValue.getOpen());
    }

}
