package com.otta.cooperative.poll.vote.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.otta.cooperative.poll.meeting.entity.PollEntity;
import com.otta.cooperative.poll.user.entity.UserEntity;
import com.otta.cooperative.poll.vote.entity.VoteEntity;
import com.otta.cooperative.poll.vote.entity.VoteOptionEntity;

@ExtendWith(MockitoExtension.class)
public class VoteEntityMapperTest {
    @InjectMocks
    private VoteEntityMapper mapper;

    @Mock
    private PollEntity pollEntity;
    @Mock
    private UserEntity userEntity;
    @Mock
    private VoteOptionEntity voteOptionEntity;

    @Test
    public void shouldCorrectlyMapVoteEntity() {
        // given
        // when
        VoteEntity actualValue = mapper.map(pollEntity, userEntity, voteOptionEntity);
        // then
        assertEquals(pollEntity, actualValue.getPollEntity());
        assertEquals(userEntity, actualValue.getUserEntity());
        assertEquals(voteOptionEntity, actualValue.getVoteOptionEntity());
    }

}
