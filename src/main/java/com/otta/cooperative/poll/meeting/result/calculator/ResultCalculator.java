package com.otta.cooperative.poll.meeting.result.calculator;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.otta.cooperative.poll.meeting.model.result.VoteOptionResultOutput;

@Component
public class ResultCalculator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResultCalculator.class);

    private static final int LIST_SIZE_ONE = 1;
    private static final String WINNER = "WINNER %s";
    private static final String DRAW = "DRAW";

    public String calculate(Collection<VoteOptionResultOutput> voteOptionResultOutputs) {
        Optional<VoteOptionResultOutput> optionalWinner = voteOptionResultOutputs.stream()
                .max(Comparator.comparing(VoteOptionResultOutput::getCount));

        if (optionalWinner.isPresent()) {
            VoteOptionResultOutput winner = optionalWinner.get();
            Long winnerCount = winner.getCount();
            String winnerLabel = winner.getLabel();

            if (voteOptionResultOutputs.stream()
                    .filter(voteOptionsResult -> voteOptionsResult.getCount().equals(winnerCount))
                    .count() == LIST_SIZE_ONE) {
                LOGGER.debug("Most voted options: {}", winnerLabel);
                return String.format(WINNER, winnerLabel);
            }
        }
        return DRAW;
    }

}
