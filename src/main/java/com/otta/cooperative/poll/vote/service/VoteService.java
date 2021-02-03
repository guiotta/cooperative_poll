package com.otta.cooperative.poll.vote.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.otta.cooperative.poll.meeting.entity.MeetingEntity;
import com.otta.cooperative.poll.meeting.entity.PollEntity;
import com.otta.cooperative.poll.meeting.extractor.PollEntityExtractor;
import com.otta.cooperative.poll.meeting.repository.MeetingRepository;
import com.otta.cooperative.poll.user.converter.UserEntityLoggedConverter;
import com.otta.cooperative.poll.user.entity.UserEntity;
import com.otta.cooperative.poll.vote.client.model.Status;
import com.otta.cooperative.poll.vote.client.model.StatusResource;
import com.otta.cooperative.poll.vote.client.rest.StatusClient;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(VoteService.class);

    private final VoteRepository voteRepository;
    private final VoteOptionRepository voteOptionRepository;
    private final MeetingRepository meetingRepository;
    private final PollEntityExtractor pollEntityExtractor;
    private final PollOpenValidator pollOpenValidation;
    private final UserEntityLoggedConverter userEntityLoggedConverter;
    private final VoteOutputMapper voteOutputMapper;
    private final VoteEntityMapper voteEntityMapper;
    private final VoteOptionOutputMapper voteOptionOutputMapper;
    private final StatusClient statusClient;

    public VoteService(VoteRepository voteRepository, VoteOptionRepository voteOptionRepository,
            MeetingRepository meetingRepository, PollEntityExtractor pollEntityExtractor,
            PollOpenValidator pollOpenValidation, UserEntityLoggedConverter userEntityLoggedConverter,
            VoteOutputMapper voteOutputMapper, VoteEntityMapper voteEntityMapper,
            VoteOptionOutputMapper voteOptionOutputMapper, StatusClient statusClient) {
        this.voteRepository = voteRepository;
        this.voteOptionRepository = voteOptionRepository;
        this.meetingRepository = meetingRepository;
        this.pollEntityExtractor = pollEntityExtractor;
        this.pollOpenValidation = pollOpenValidation;
        this.userEntityLoggedConverter = userEntityLoggedConverter;
        this.voteOutputMapper = voteOutputMapper;
        this.voteEntityMapper = voteEntityMapper;
        this.voteOptionOutputMapper = voteOptionOutputMapper;
        this.statusClient = statusClient;
    }

    public VoteOutput saveVote(VoteInput input) {
        LocalDateTime dateTimeToProcessVote = this.getLocalDateTimeNow();
        Optional<VoteOptionEntity> optionalVoteOptionEntity = voteOptionRepository.findById(input.getVoteOptionId());
        Optional<UserEntity> optionalUserEntity = userEntityLoggedConverter.convert();
        Optional<MeetingEntity> optionalMeetingEntity = meetingRepository.findById(input.getMeetingId());
        Optional<PollEntity> optionalPollEntity = pollEntityExtractor.extract(optionalMeetingEntity);
        LOGGER.debug("Search in database result: VoteOptionEntity {}, UserEntity {}, MeetingEntity {}.",
                optionalVoteOptionEntity, optionalUserEntity, optionalMeetingEntity);

        if (optionalVoteOptionEntity.isPresent() && optionalUserEntity.isPresent() && optionalPollEntity.isPresent()) {

            if (pollOpenValidation.validate(optionalPollEntity.get(), dateTimeToProcessVote)) {
                UserEntity userEntity = optionalUserEntity.get();
                Boolean isValidDocument = this.isValidDocument(userEntity.getDocument());

                if (isValidDocument) {
                    VoteEntity voteEntity = voteEntityMapper.map(optionalPollEntity.get(),
                            userEntity,
                            optionalVoteOptionEntity.get());

                    voteEntity = voteRepository.save(voteEntity);
                    return voteOutputMapper.map(voteEntity);
                }
                throw new IllegalArgumentException("User document is not allowed to vote.");
            }
            throw new IllegalArgumentException("Could not process vote for this poll. Session is already over.");
        }
        throw new IllegalArgumentException("Could not find minimal information to vote.");
    }

    public Collection<VoteOptionOutput> listVoteOptions() {
        Collection<VoteOptionEntity> voteOptions = voteOptionRepository.findAll();

        return voteOptions.stream().map(option -> voteOptionOutputMapper.map(option)).collect(Collectors.toList());
    }

    private Boolean isValidDocument(String document) {
        StatusResource resource = statusClient.findByDocument(document);
        String status = resource.getStatus();
        return Status.ABLE_TO_VOTE.getValue().equals(status);
    }

    protected LocalDateTime getLocalDateTimeNow() {
        return LocalDateTime.now();
    }
}
