package com.otta.cooperative.poll.vote.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.otta.cooperative.poll.vote.entity.VoteEntity;
import com.otta.cooperative.poll.vote.model.VoteOutput;

@Component
public class VoteOutputMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(VoteOutputMapper.class);

    public VoteOutput map(VoteEntity entity) {
        VoteOutput output = new VoteOutput(entity.getId(), entity.getVoteOptionEntity().getId(),
                entity.getVoteOptionEntity().getLabel());

        LOGGER.info("Mapping VoteEntity {} to VoteOutput {}.", entity, output);

        return output;
    }
}
