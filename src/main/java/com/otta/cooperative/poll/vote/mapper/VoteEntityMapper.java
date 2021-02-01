package com.otta.cooperative.poll.vote.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.otta.cooperative.poll.meeting.entity.PollEntity;
import com.otta.cooperative.poll.user.entity.UserEntity;
import com.otta.cooperative.poll.vote.entity.VoteEntity;
import com.otta.cooperative.poll.vote.entity.VoteOptionEntity;

@Component
public class VoteEntityMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(VoteEntityMapper.class);

    public VoteEntity map(PollEntity pollEntity, UserEntity userEntity, VoteOptionEntity voteOptionEntity) {
        VoteEntity entity = new VoteEntity(null, pollEntity, voteOptionEntity, userEntity);

        LOGGER.info("Mapping PollEntity {}, UserEntity {} and VoteOptionEntity {} to VoteEntity {}.", pollEntity,
                userEntity, voteOptionEntity, entity);

        return entity;
    }
}
