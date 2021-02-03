package com.otta.cooperative.poll.meeting.result.mapper;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.otta.cooperative.poll.meeting.entity.PollEntity;
import com.otta.cooperative.poll.meeting.model.result.ResultOutput;
import com.otta.cooperative.poll.meeting.model.result.VoteOptionResultOutput;
import com.otta.cooperative.poll.meeting.result.calculator.ResultCalculator;
import com.otta.cooperative.poll.vote.entity.VoteOptionEntity;

@Component
public class ResultOutputMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResultOutputMapper.class);

    private final VoteOptionResultOutputCollectionMapper voteOptionResultOutputCollectionMapper;
    private final ResultCalculator resultCalculator;

    public ResultOutputMapper(VoteOptionResultOutputCollectionMapper voteOptionResultOutputCollectionMapper,
            ResultCalculator resultCalculator) {
        this.voteOptionResultOutputCollectionMapper = voteOptionResultOutputCollectionMapper;
        this.resultCalculator = resultCalculator;
    }

    public ResultOutput map(Long meetingId, PollEntity pollEntity, Collection<VoteOptionEntity> voteOptions) {
        Collection<VoteOptionResultOutput> voteOptionResultOutputs = voteOptionResultOutputCollectionMapper.map(pollEntity, voteOptions);
        String resultMessage = resultCalculator.calculate(voteOptionResultOutputs);
        ResultOutput output = new ResultOutput(meetingId, pollEntity.getEnabled(), pollEntity.getClose(), voteOptionResultOutputs, resultMessage);
        LOGGER.info("Mapping PollEntity {} and VoteOptions {} to ResultOutput {}.", pollEntity, voteOptions, output);

        return output;
    }
}
