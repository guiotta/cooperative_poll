package com.otta.cooperative.poll.meeting.result.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import org.assertj.core.util.Lists;
import org.eclipse.collections.api.map.MapIterable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.otta.cooperative.poll.meeting.entity.PollEntity;
import com.otta.cooperative.poll.vote.entity.VoteEntity;
import com.otta.cooperative.poll.vote.entity.VoteOptionEntity;

@ExtendWith(MockitoExtension.class)
public class VoteOptionEntityCountMapMapperTest {
    private static final int ONE_VOTE = 1;
    private static final int TWO_VOTES = 2;

    @InjectMocks
    private VoteOptionEntityCountMapMapper mapper;

    @Mock
    private PollEntity pollEntity;
    @Mock
    private VoteEntity voteEntityA;
    @Mock
    private VoteEntity voteEntityB;
    @Mock
    private VoteEntity voteEntityC;
    @Mock
    private VoteOptionEntity voteOptionEntityA;
    @Mock
    private VoteOptionEntity voteOptionEntityB;

    @BeforeEach
    protected void setUp() {
        given(pollEntity.getVotes()).willReturn(Lists.list(voteEntityA, voteEntityB, voteEntityC));
        given(voteEntityA.getVoteOptionEntity()).willReturn(voteOptionEntityA);
        given(voteEntityB.getVoteOptionEntity()).willReturn(voteOptionEntityB);
        given(voteEntityC.getVoteOptionEntity()).willReturn(voteOptionEntityB);
    }

    @Test
    public void shouldCorrectlyMapMapIterable() {
        // given
        // when
        MapIterable<VoteOptionEntity, Integer> actualValue = mapper.map(pollEntity);
        // then
        assertEquals(ONE_VOTE, actualValue.get(voteOptionEntityA));
        assertEquals(TWO_VOTES, actualValue.get(voteOptionEntityB));
    }

}
