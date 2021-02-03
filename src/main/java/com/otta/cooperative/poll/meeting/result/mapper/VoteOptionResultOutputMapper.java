package com.otta.cooperative.poll.meeting.result.mapper;

import org.eclipse.collections.api.map.MapIterable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.otta.cooperative.poll.meeting.model.result.VoteOptionResultOutput;
import com.otta.cooperative.poll.vote.entity.VoteOptionEntity;

@Component
public class VoteOptionResultOutputMapper {
    private static final Integer ZERO_VOTES = Integer.valueOf(0);

    private static final Logger LOGGER = LoggerFactory.getLogger(VoteOptionResultOutputMapper.class);

    public VoteOptionResultOutput map(VoteOptionEntity voteOption, MapIterable<VoteOptionEntity, Integer> votesMap) {
        int votesCount = votesMap.getIfAbsentValue(voteOption, ZERO_VOTES);
        VoteOptionResultOutput output = new VoteOptionResultOutput(voteOption.getId(), voteOption.getLabel(), Long.valueOf(votesCount));
        LOGGER.debug("Mapping ViteOptionEntity {} and MapIterable {}  to VoteOptionResultOutput {}.", voteOption, votesMap, output);

        return output;
    }
}
