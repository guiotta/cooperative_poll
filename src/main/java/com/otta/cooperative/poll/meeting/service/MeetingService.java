package com.otta.cooperative.poll.meeting.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.otta.cooperative.poll.meeting.entity.MeetingEntity;
import com.otta.cooperative.poll.meeting.entity.PollEntity;
import com.otta.cooperative.poll.meeting.mapper.MeetingEntityMapper;
import com.otta.cooperative.poll.meeting.mapper.MeetingOutputMapper;
import com.otta.cooperative.poll.meeting.model.MeetingInput;
import com.otta.cooperative.poll.meeting.model.MeetingOutput;
import com.otta.cooperative.poll.meeting.model.poll.PollInput;
import com.otta.cooperative.poll.meeting.model.poll.PollOutput;
import com.otta.cooperative.poll.meeting.repository.MeetingRepository;

@Service
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final MeetingEntityMapper meetingEntityMapper;
    private final MeetingOutputMapper meetingOutputMapper;

    public MeetingService(MeetingRepository meetingRepository, MeetingEntityMapper meetingEntityMapper,
            MeetingOutputMapper meetingOutputMapper) {
        this.meetingRepository = meetingRepository;
        this.meetingEntityMapper = meetingEntityMapper;
        this.meetingOutputMapper = meetingOutputMapper;
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
                PollEntity pollEntity = map(input, meetingEntity);
                meetingEntity.setPoll(pollEntity);
                meetingEntity = meetingRepository.save(meetingEntity);

                return map(meetingEntity.getPoll());
            }
            throw new IllegalStateException(String.format("MeetingEntity with Id %d has PollEntity with Id %d.",
                    meetingEntity.getId(), meetingEntity.getPoll().getId()));
        }
        throw new IllegalArgumentException(String.format("Could not find any Meeting with id %d.", input.getMeetingId()));
    }

    private PollEntity map(PollInput pollInput, MeetingEntity meetingEntity) {
        LocalDateTime pollStartDateTime = LocalDateTime.now();
        Boolean isEnabled = Boolean.TRUE;
        LocalDateTime pollEndDateTime = pollStartDateTime.plusSeconds(pollInput.getTimeToEndInSeconds());
        PollEntity pollEntity = new PollEntity();

        pollEntity.setOpen(pollStartDateTime);
        pollEntity.setClose(pollEndDateTime);
        pollEntity.setEnabled(isEnabled);
        pollEntity.setMeeting(meetingEntity);
        return pollEntity;
    }

    private PollOutput map(PollEntity entity) {
        PollOutput output = new PollOutput(entity.getId(), entity.getMeeting().getId(), entity.getOpen(), entity.getClose());

        return output;
    }
}
