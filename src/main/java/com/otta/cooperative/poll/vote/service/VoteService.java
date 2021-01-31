package com.otta.cooperative.poll.vote.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.otta.cooperative.poll.meeting.entity.PollEntity;
import com.otta.cooperative.poll.meeting.repository.PollRepository;
import com.otta.cooperative.poll.user.entity.UserEntity;
import com.otta.cooperative.poll.user.repository.UserRepository;
import com.otta.cooperative.poll.vote.entity.VoteEntity;
import com.otta.cooperative.poll.vote.entity.VoteOptionEntity;
import com.otta.cooperative.poll.vote.model.VoteInput;
import com.otta.cooperative.poll.vote.model.VoteOutput;
import com.otta.cooperative.poll.vote.repository.VoteOptionRepository;
import com.otta.cooperative.poll.vote.repository.VoteRepository;

@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final VoteOptionRepository voteOptionRepository;
    private final UserRepository userRepository;
    private final PollRepository pollRepository;

    public VoteService(VoteRepository voteRepository, VoteOptionRepository voteOptionRepository,
            UserRepository userRepository, PollRepository pollRepository) {
        this.voteRepository = voteRepository;
        this.voteOptionRepository = voteOptionRepository;
        this.userRepository = userRepository;
        this.pollRepository = pollRepository;
    }

    public VoteOutput saveVote(VoteInput input) {
        Optional<VoteOptionEntity> optionalVoteOptionEntity = voteOptionRepository.findById(input.getVoteOptionId());
        Optional<UserEntity> optionalUserEntity = userRepository.findById(input.getUserId());
        Optional<PollEntity> optionalPollEntity = pollRepository.findById(input.getPollId());

        if (optionalVoteOptionEntity.isPresent() && optionalUserEntity.isPresent() && optionalPollEntity.isPresent()) {
            VoteEntity voteEntity = new VoteEntity();
            voteEntity.setPollEntity(optionalPollEntity.get());
            voteEntity.setUserEntity(optionalUserEntity.get());
            voteEntity.setVoteOptionEntity(optionalVoteOptionEntity.get());

            voteEntity = voteRepository.save(voteEntity);
            return map(voteEntity);
        }
        throw new IllegalArgumentException("Could not find minimal information to vote.");
    }

    private VoteOutput map(VoteEntity entity) {
        return new VoteOutput(entity.getId(), entity.getVoteOptionEntity().getId(), entity.getVoteOptionEntity().getLabel());
    }
}
