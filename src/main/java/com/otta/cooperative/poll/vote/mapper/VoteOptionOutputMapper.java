package com.otta.cooperative.poll.vote.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.otta.cooperative.poll.vote.entity.VoteOptionEntity;
import com.otta.cooperative.poll.vote.model.option.VoteOptionOutput;

@Component
public class VoteOptionOutputMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(VoteOptionOutputMapper.class);

    public VoteOptionOutput map(VoteOptionEntity entity) {
        VoteOptionOutput output = new VoteOptionOutput(entity.getId(), entity.getLabel());
        LOGGER.info("Mapping VoteOptionEntity {} to VoteOptionOutput {}.", entity, output);

        return output;
    }
}
