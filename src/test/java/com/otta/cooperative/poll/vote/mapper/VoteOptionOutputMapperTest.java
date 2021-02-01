package com.otta.cooperative.poll.vote.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.otta.cooperative.poll.vote.entity.VoteOptionEntity;
import com.otta.cooperative.poll.vote.model.option.VoteOptionOutput;

@ExtendWith(MockitoExtension.class)
public class VoteOptionOutputMapperTest {
    private static final Long ID = 100l;
    private static final String LABEL = "LABEL";

    @InjectMocks
    private VoteOptionOutputMapper mapper;

    @Mock
    private VoteOptionEntity voteOptionEntity;

    @BeforeEach
    protected void setUp() {
        given(voteOptionEntity.getId()).willReturn(ID);
        given(voteOptionEntity.getLabel()).willReturn(LABEL);
    }

    @Test
    public void shouldCorrectlyMapVoteOptionOutput() {
        // given
        // when
        VoteOptionOutput actualValue = mapper.map(voteOptionEntity);
        // then
        assertEquals(ID, actualValue.getId());
        assertEquals(LABEL, actualValue.getLabel());
    }

}
