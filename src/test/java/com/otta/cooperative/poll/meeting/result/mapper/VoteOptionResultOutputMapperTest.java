package com.otta.cooperative.poll.meeting.result.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import org.eclipse.collections.api.map.MapIterable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.otta.cooperative.poll.meeting.model.result.VoteOptionResultOutput;
import com.otta.cooperative.poll.vote.entity.VoteOptionEntity;

@ExtendWith(MockitoExtension.class)
public class VoteOptionResultOutputMapperTest {
    private static final Integer ZERO_VOTES = Integer.valueOf(0);
    private static final int VOTES = 5;
    private static final Long ID = 100l;
    private static final String LABEL = "label";

    @InjectMocks
    private VoteOptionResultOutputMapper mapper;

    @Mock
    private VoteOptionEntity voteOptionEntity;
    @Mock
    private MapIterable<VoteOptionEntity, Integer> votesMap;

    @BeforeEach
    protected void setUp() {
        given(votesMap.getIfAbsentValue(voteOptionEntity, ZERO_VOTES)).willReturn(VOTES);
        given(voteOptionEntity.getId()).willReturn(ID);
        given(voteOptionEntity.getLabel()).willReturn(LABEL);
    }

    @Test
    public void shouldCorrectlyMapVoteOptionResultOutput() {
        // given
        // when
        VoteOptionResultOutput actualValue = mapper.map(voteOptionEntity, votesMap);
        // then
        assertEquals(ID, actualValue.getId());
        assertEquals(LABEL, actualValue.getLabel());
        assertEquals(VOTES, actualValue.getCount());
    }

}
