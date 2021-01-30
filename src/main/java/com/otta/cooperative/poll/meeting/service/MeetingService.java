package com.otta.cooperative.poll.meeting.service;

import org.springframework.stereotype.Service;

import com.otta.cooperative.poll.meeting.entity.MeetingEntity;
import com.otta.cooperative.poll.meeting.model.MeetingInput;
import com.otta.cooperative.poll.meeting.model.MeetingOutput;
import com.otta.cooperative.poll.meeting.repository.MeetingRepository;

@Service
public class MeetingService {
    private final MeetingRepository meetingRepository;

    public MeetingService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public MeetingOutput save(MeetingInput input) {
        MeetingEntity entity = map(input);
        entity = meetingRepository.save(entity);

        return map(entity);
    }

    private MeetingEntity map(MeetingInput input) {
        MeetingEntity entity = new MeetingEntity();
        entity.setSubject(input.getSubject());

        return entity;
    }
    private MeetingOutput map(MeetingEntity entity) {
        MeetingOutput output = new MeetingOutput(entity.getId(), entity.getSubject());
        
        return output;
    }
}
