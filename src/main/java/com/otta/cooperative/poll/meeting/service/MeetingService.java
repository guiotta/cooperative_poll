package com.otta.cooperative.poll.meeting.service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.otta.cooperative.poll.meeting.entity.MeetingEntity;
import com.otta.cooperative.poll.meeting.entity.PollEntity;
import com.otta.cooperative.poll.meeting.extractor.PollEntityExtractor;
import com.otta.cooperative.poll.meeting.job.PollEndJobScheduler;
import com.otta.cooperative.poll.meeting.mapper.MeetingEntityMapper;
import com.otta.cooperative.poll.meeting.mapper.MeetingOutputMapper;
import com.otta.cooperative.poll.meeting.mapper.PollEntityMapper;
import com.otta.cooperative.poll.meeting.mapper.PollOutputMapper;
import com.otta.cooperative.poll.meeting.model.MeetingInput;
import com.otta.cooperative.poll.meeting.model.MeetingOutput;
import com.otta.cooperative.poll.meeting.model.poll.PollInput;
import com.otta.cooperative.poll.meeting.model.poll.PollOutput;
import com.otta.cooperative.poll.meeting.model.result.ResultOutput;
import com.otta.cooperative.poll.meeting.repository.MeetingRepository;
import com.otta.cooperative.poll.meeting.result.mapper.ResultOutputMapper;
import com.otta.cooperative.poll.vote.entity.VoteOptionEntity;
import com.otta.cooperative.poll.vote.repository.VoteOptionRepository;

@Service
public class MeetingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MeetingService.class);

    private final MeetingRepository meetingRepository;
    private final MeetingEntityMapper meetingEntityMapper;
    private final MeetingOutputMapper meetingOutputMapper;
    private final PollEntityMapper pollEntityMapper;
    private final PollOutputMapper pollOutputMapper;
    private final PollEndJobScheduler pollEndJobScheduler;
    private final VoteOptionRepository voteOptionRepository;
    private final PollEntityExtractor pollEntityExtractor;
    private final ResultOutputMapper resultOutputMapper;

    public MeetingService(MeetingRepository meetingRepository, MeetingEntityMapper meetingEntityMapper,
            MeetingOutputMapper meetingOutputMapper, PollEntityMapper pollEntityMapper,
            PollOutputMapper pollOutputMapper, PollEndJobScheduler pollEndJobScheduler,
            VoteOptionRepository voteOptionRepository, PollEntityExtractor pollEntityExtractor,
            ResultOutputMapper resultOutputMapper) {
        this.meetingRepository = meetingRepository;
        this.meetingEntityMapper = meetingEntityMapper;
        this.meetingOutputMapper = meetingOutputMapper;
        this.pollEntityMapper = pollEntityMapper;
        this.pollOutputMapper = pollOutputMapper;
        this.pollEndJobScheduler = pollEndJobScheduler;
        this.voteOptionRepository = voteOptionRepository;
        this.pollEntityExtractor = pollEntityExtractor;
        this.resultOutputMapper = resultOutputMapper;
    }

    public MeetingOutput saveMeeting(MeetingInput input) {
        MeetingEntity entity = meetingEntityMapper.map(input);
        entity = meetingRepository.save(entity);

        return meetingOutputMapper.map(entity);
    }

    public Collection<MeetingOutput> findAll() {
        Collection<MeetingEntity> entities = meetingRepository.findAll();

        return entities.stream()
                .map(entity -> meetingOutputMapper.map(entity))
                .collect(Collectors.toList());
    }

    public PollOutput savePoll(PollInput input) throws SchedulerException {
        Optional<MeetingEntity> optionalMeeting = meetingRepository.findById(input.getMeetingId());

        if (optionalMeeting.isPresent()) {
            MeetingEntity meetingEntity = optionalMeeting.get();

            if (meetingEntity.getPoll() == null) {
                LOGGER.info("Adding a Poll to Meeting with Id {}.", meetingEntity.getId());

                PollEntity pollEntity = pollEntityMapper.map(input, meetingEntity);
                meetingEntity.setPoll(pollEntity);
                meetingEntity = meetingRepository.save(meetingEntity);

                pollEndJobScheduler.scheduleJob(pollEntity.getClose(), pollEntity.getId());

                return pollOutputMapper.map(meetingEntity.getPoll());
            }
            LOGGER.debug("Meeting with Id {} already has a poll. Poll Id {}.", meetingEntity.getId(), meetingEntity.getPoll().getId());
            throw new IllegalStateException(String.format("Meeting already has a Poll associated."));
        }
        LOGGER.debug("Could not find any Meeting with id {}.", input.getMeetingId());
        throw new IllegalArgumentException(String.format("Could not find a Meeting to add a Poll."));
    }

    public ResultOutput generateResult(Long meetingId) {
        Optional<MeetingEntity> optionalMeetingEntity = meetingRepository.findById(meetingId);
        Collection<VoteOptionEntity> voteOptions = voteOptionRepository.findAll();
        Optional<PollEntity> optionalPollEntity = pollEntityExtractor.extract(optionalMeetingEntity);

        if (optionalPollEntity.isPresent()) {
            PollEntity pollEntity = optionalPollEntity.get();

            return resultOutputMapper.map(meetingId, pollEntity, voteOptions);
        }
        throw new IllegalArgumentException(String.format("Could not find a meeting or poll with id %d.", meetingId));
    }
}
