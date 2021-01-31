package com.otta.cooperative.poll.vote.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.otta.cooperative.poll.meeting.entity.PollEntity;
import com.otta.cooperative.poll.meeting.repository.PollRepository;
import com.otta.cooperative.poll.user.converter.UserEntityLoggedConverter;
import com.otta.cooperative.poll.user.entity.UserEntity;
import com.otta.cooperative.poll.vote.entity.VoteEntity;
import com.otta.cooperative.poll.vote.entity.VoteOptionEntity;
import com.otta.cooperative.poll.vote.model.VoteInput;
import com.otta.cooperative.poll.vote.model.VoteOutput;
import com.otta.cooperative.poll.vote.repository.VoteOptionRepository;
import com.otta.cooperative.poll.vote.repository.VoteRepository;
import com.otta.cooperative.poll.vote.validation.PollOpenValidation;

@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final VoteOptionRepository voteOptionRepository;
    private final PollRepository pollRepository;
    private final PollOpenValidation pollOpenValidation;
    private final UserEntityLoggedConverter userEntityLoggedConverter;

    public VoteService(VoteRepository voteRepository, VoteOptionRepository voteOptionRepository,
            PollRepository pollRepository, PollOpenValidation pollOpenValidation,
            UserEntityLoggedConverter userEntityLoggedConverter) {
        this.voteRepository = voteRepository;
        this.voteOptionRepository = voteOptionRepository;
        this.pollRepository = pollRepository;
        this.pollOpenValidation = pollOpenValidation;
        this.userEntityLoggedConverter = userEntityLoggedConverter;
    }

    public VoteOutput saveVote(VoteInput input) {
        LocalDateTime dateTimeToProcessVote = LocalDateTime.now();
        Optional<VoteOptionEntity> optionalVoteOptionEntity = voteOptionRepository.findById(input.getVoteOptionId());
        Optional<UserEntity> optionalUserEntity = userEntityLoggedConverter.convert();
        Optional<PollEntity> optionalPollEntity = pollRepository.findById(input.getPollId());

        if (optionalVoteOptionEntity.isPresent() && optionalUserEntity.isPresent() && optionalPollEntity.isPresent()) {

            if (pollOpenValidation.validate(optionalPollEntity.get(), dateTimeToProcessVote)) {
                VoteEntity voteEntity = new VoteEntity();
                voteEntity.setPollEntity(optionalPollEntity.get());
                voteEntity.setUserEntity(optionalUserEntity.get());
                voteEntity.setVoteOptionEntity(optionalVoteOptionEntity.get());

                voteEntity = voteRepository.save(voteEntity);
                return map(voteEntity);
            }
            throw new IllegalArgumentException("Could not process vote for this poll. Session is already over.");
        }
        throw new IllegalArgumentException("Could not find minimal information to vote.");
    }

    private VoteOutput map(VoteEntity entity) {
        return new VoteOutput(entity.getId(), entity.getVoteOptionEntity().getId(), entity.getVoteOptionEntity().getLabel());
    }
}
