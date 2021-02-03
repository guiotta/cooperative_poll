package com.otta.cooperative.poll.meeting.result.mapper;

import java.util.Collection;
import java.util.stream.Collectors;

import org.eclipse.collections.api.map.MapIterable;
import org.springframework.stereotype.Component;

import com.otta.cooperative.poll.meeting.entity.PollEntity;
import com.otta.cooperative.poll.meeting.model.result.VoteOptionResultOutput;
import com.otta.cooperative.poll.vote.entity.VoteOptionEntity;

/**
 * Componente para mapear um {@link PollEntity} e uma {@link Collection} de
 * {@link VoteOptionEntity} em uma {@link Collection} de {@link VoteOptionResultOutput}.
 * 
 * @author guilherme
 *
 */
@Component
public class VoteOptionResultOutputCollectionMapper {
    private final VoteOptionResultOutputMapper voteOptionResultOutputMapper;
    private final VoteOptionEntityCountMapMapper voteOptionEntityCountMapMapper;

    public VoteOptionResultOutputCollectionMapper(VoteOptionResultOutputMapper voteOptionResultOutputMapper,
            VoteOptionEntityCountMapMapper voteOptionEntityCountMapMapper) {
        this.voteOptionResultOutputMapper = voteOptionResultOutputMapper;
        this.voteOptionEntityCountMapMapper = voteOptionEntityCountMapMapper;
    }

    public Collection<VoteOptionResultOutput> map(PollEntity pollEntity, Collection<VoteOptionEntity> voteOptions) {
        MapIterable<VoteOptionEntity, Integer> votesMap = voteOptionEntityCountMapMapper.map(pollEntity);

        return voteOptions.stream()
                .map(voteOption -> voteOptionResultOutputMapper.map(voteOption, votesMap))
                .collect(Collectors.toSet());
    }
}
