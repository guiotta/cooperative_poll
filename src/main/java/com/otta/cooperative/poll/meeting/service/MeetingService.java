package com.otta.cooperative.poll.meeting.service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.otta.cooperative.poll.meeting.entity.MeetingEntity;
import com.otta.cooperative.poll.meeting.entity.PollEntity;
import com.otta.cooperative.poll.meeting.mapper.MeetingEntityMapper;
import com.otta.cooperative.poll.meeting.mapper.MeetingOutputMapper;
import com.otta.cooperative.poll.meeting.mapper.PollEntityMapper;
import com.otta.cooperative.poll.meeting.mapper.PollOutputMapper;
import com.otta.cooperative.poll.meeting.model.MeetingInput;
import com.otta.cooperative.poll.meeting.model.MeetingOutput;
import com.otta.cooperative.poll.meeting.model.poll.PollInput;
import com.otta.cooperative.poll.meeting.model.poll.PollOutput;
import com.otta.cooperative.poll.meeting.repository.MeetingRepository;

@Service
public class MeetingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MeetingService.class);

    
    private final MeetingRepository meetingRepository;
    private final MeetingEntityMapper meetingEntityMapper;
    private final MeetingOutputMapper meetingOutputMapper;
    private final PollEntityMapper pollEntityMapper;
    private final PollOutputMapper pollOutputMapper;

    public MeetingService(MeetingRepository meetingRepository, MeetingEntityMapper meetingEntityMapper,
            MeetingOutputMapper meetingOutputMapper, PollEntityMapper pollEntityMapper,
            PollOutputMapper pollOutputMapper) {
        this.meetingRepository = meetingRepository;
        this.meetingEntityMapper = meetingEntityMapper;
        this.meetingOutputMapper = meetingOutputMapper;
        this.pollEntityMapper = pollEntityMapper;
        this.pollOutputMapper = pollOutputMapper;
    }

    public MeetingOutput save(MeetingInput input) {
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

    public PollOutput save(PollInput input) {
        Optional<MeetingEntity> optionalMeeting = meetingRepository.findById(input.getMeetingId());

        if (optionalMeeting.isPresent()) {
            MeetingEntity meetingEntity = optionalMeeting.get();

            if (meetingEntity.getPoll() == null) {
                LOGGER.info("Adding a Poll to Meeting with Id {}.", meetingEntity.getId());

                PollEntity pollEntity = pollEntityMapper.map(input, meetingEntity);
                meetingEntity.setPoll(pollEntity);
                meetingEntity = meetingRepository.save(meetingEntity);

                return pollOutputMapper.map(meetingEntity.getPoll());
            }
            LOGGER.debug("Meeting with Id {} already has a poll. Poll Id {}.", meetingEntity.getId(), meetingEntity.getPoll().getId());
            throw new IllegalStateException(String.format("Meeting already has a Poll associated."));
        }
        LOGGER.debug("Could not find any Meeting with id {}.", input.getMeetingId());
        throw new IllegalArgumentException(String.format("Could not find a Meeting to add a Poll."));
    }
}
