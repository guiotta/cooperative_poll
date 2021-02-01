package com.otta.cooperative.poll.meeting.result.mapper;

import org.eclipse.collections.api.bag.Bag;
import org.eclipse.collections.api.map.MapIterable;
import org.eclipse.collections.impl.collector.Collectors2;
import org.springframework.stereotype.Component;

import com.otta.cooperative.poll.meeting.entity.PollEntity;
import com.otta.cooperative.poll.vote.entity.VoteOptionEntity;

@Component
public class VoteOptionEntityCountMapMapper {

    public MapIterable<VoteOptionEntity,Integer> map(PollEntity pollEntity) {
        Bag<VoteOptionEntity> votesBag = pollEntity.getVotes().stream()
                .map(vote -> vote.getVoteOptionEntity())
                .collect(Collectors2.countBy(voteOption -> voteOption));
        return votesBag.toMapOfItemToCount();
    }
}
