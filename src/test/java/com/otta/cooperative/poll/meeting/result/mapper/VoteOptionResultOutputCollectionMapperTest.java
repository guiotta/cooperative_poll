package com.otta.cooperative.poll.meeting.result.mapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Collection;

import org.assertj.core.util.Lists;
import org.eclipse.collections.api.map.MapIterable;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.otta.cooperative.poll.meeting.entity.PollEntity;
import com.otta.cooperative.poll.meeting.model.result.VoteOptionResultOutput;
import com.otta.cooperative.poll.vote.entity.VoteOptionEntity;

@ExtendWith(MockitoExtension.class)
public class VoteOptionResultOutputCollectionMapperTest {
    @InjectMocks
    private VoteOptionResultOutputCollectionMapper mapper;

    @Mock
    private VoteOptionResultOutputMapper voteOptionResultOutputMapper;
    @Mock
    private VoteOptionEntityCountMapMapper voteOptionEntityCountMapMapper;

    @Mock
    private PollEntity polEntity;
    @Mock
    private VoteOptionEntity voteOptionEntity;
    @Mock
    private MapIterable<VoteOptionEntity, Integer> votesMap;
    @Mock
    private VoteOptionResultOutput voteOptionResultOutput;

    @BeforeEach
    protected void setUp() {
        given(voteOptionEntityCountMapMapper.map(polEntity)).willReturn(votesMap);
        given(voteOptionResultOutputMapper.map(voteOptionEntity, votesMap)).willReturn(voteOptionResultOutput);
    }

    @Test
    public void shouldCorrectlyMapVoteOptionResultOutputCollection() {
        // given
        // when
        Collection<VoteOptionResultOutput> actualValue = mapper.map(polEntity, Lists.list(voteOptionEntity));
        // then
        assertThat(actualValue, Matchers.containsInAnyOrder(voteOptionResultOutput));
    }

}
