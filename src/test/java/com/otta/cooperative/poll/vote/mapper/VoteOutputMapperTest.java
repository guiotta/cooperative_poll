package com.otta.cooperative.poll.vote.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.otta.cooperative.poll.vote.entity.VoteEntity;
import com.otta.cooperative.poll.vote.entity.VoteOptionEntity;
import com.otta.cooperative.poll.vote.model.VoteOutput;

@ExtendWith(MockitoExtension.class)
public class VoteOutputMapperTest {
    private static final Long ID = 1l;
    private static final Long VOTE_OPTION_ID = 5l;
    private static final String VOTE_OPTION_LABEL = "LABEL";

    @InjectMocks
    private VoteOutputMapper mapper;

    @Mock
    private VoteEntity entity;
    @Mock
    private VoteOptionEntity voteOptionEntity;

    @BeforeEach
    protected void setUp() {
        given(entity.getId()).willReturn(ID);
        given(entity.getVoteOptionEntity()).willReturn(voteOptionEntity);
        given(voteOptionEntity.getId()).willReturn(VOTE_OPTION_ID);
        given(voteOptionEntity.getLabel()).willReturn(VOTE_OPTION_LABEL);
    }

    @Test
    public void testMap() throws Exception {
        // given
        // when
        VoteOutput actualValue = mapper.map(entity);
        // then
        assertEquals(ID, actualValue.getId());
        assertEquals(VOTE_OPTION_ID, actualValue.getVoteOptionId());
        assertEquals(VOTE_OPTION_LABEL, actualValue.getVoteOptionLabel());
    }

}
