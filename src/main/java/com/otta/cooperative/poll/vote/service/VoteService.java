package com.otta.cooperative.poll.vote.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.otta.cooperative.poll.meeting.entity.PollEntity;
import com.otta.cooperative.poll.meeting.repository.PollRepository;
import com.otta.cooperative.poll.user.converter.UserEntityLoggedConverter;
import com.otta.cooperative.poll.user.entity.UserEntity;
import com.otta.cooperative.poll.vote.entity.VoteEntity;
import com.otta.cooperative.poll.vote.entity.VoteOptionEntity;
import com.otta.cooperative.poll.vote.mapper.VoteEntityMapper;
import com.otta.cooperative.poll.vote.mapper.VoteOptionOutputMapper;
import com.otta.cooperative.poll.vote.mapper.VoteOutputMapper;
import com.otta.cooperative.poll.vote.model.VoteInput;
import com.otta.cooperative.poll.vote.model.VoteOutput;
import com.otta.cooperative.poll.vote.model.option.VoteOptionOutput;
import com.otta.cooperative.poll.vote.repository.VoteOptionRepository;
import com.otta.cooperative.poll.vote.repository.VoteRepository;
import com.otta.cooperative.poll.vote.validation.PollOpenValidator;

@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final VoteOptionRepository voteOptionRepository;
    private final PollRepository pollRepository;
    private final PollOpenValidator pollOpenValidation;
    private final UserEntityLoggedConverter userEntityLoggedConverter;
    private final VoteOutputMapper voteOutputMapper;
    private final VoteEntityMapper voteEntityMapper;
    private final VoteOptionOutputMapper voteOptionOutputMapper;

    public VoteService(VoteRepository voteRepository, VoteOptionRepository voteOptionRepository,
            PollRepository pollRepository, PollOpenValidator pollOpenValidation,
            UserEntityLoggedConverter userEntityLoggedConverter, VoteOutputMapper voteOutputMapper,
            VoteEntityMapper voteEntityMapper, VoteOptionOutputMapper voteOptionOutputMapper) {
        this.voteRepository = voteRepository;
        this.voteOptionRepository = voteOptionRepository;
        this.pollRepository = pollRepository;
        this.pollOpenValidation = pollOpenValidation;
        this.userEntityLoggedConverter = userEntityLoggedConverter;
        this.voteOutputMapper = voteOutputMapper;
        this.voteEntityMapper = voteEntityMapper;
        this.voteOptionOutputMapper = voteOptionOutputMapper;
    }

    public VoteOutput saveVote(VoteInput input) {
        LocalDateTime dateTimeToProcessVote = this.getLocalDateTimeNow();
        Optional<VoteOptionEntity> optionalVoteOptionEntity = voteOptionRepository.findById(input.getVoteOptionId());
        Optional<UserEntity> optionalUserEntity = userEntityLoggedConverter.convert();
        Optional<PollEntity> optionalPollEntity = pollRepository.findById(input.getPollId());

        if (optionalVoteOptionEntity.isPresent() && optionalUserEntity.isPresent() && optionalPollEntity.isPresent()) {

            if (pollOpenValidation.validate(optionalPollEntity.get(), dateTimeToProcessVote)) {
                VoteEntity voteEntity = voteEntityMapper.map(optionalPollEntity.get(),
                        optionalUserEntity.get(),
                        optionalVoteOptionEntity.get());

                voteEntity = voteRepository.save(voteEntity);
                return voteOutputMapper.map(voteEntity);
            }
            throw new IllegalArgumentException("Could not process vote for this poll. Session is already over.");
        }
        throw new IllegalArgumentException("Could not find minimal information to vote.");
    }

    public Collection<VoteOptionOutput> listVoteOptions() {
        Collection<VoteOptionEntity> voteOptions = voteOptionRepository.findAll();

        return voteOptions.stream().map(option -> voteOptionOutputMapper.map(option)).collect(Collectors.toList());
    }

    protected LocalDateTime getLocalDateTimeNow() {
        return LocalDateTime.now();
    }
}
