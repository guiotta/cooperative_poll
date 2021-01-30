package com.otta.cooperative.poll.meeting.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.otta.cooperative.poll.meeting.entity.MeetingEntity;
import com.otta.cooperative.poll.meeting.model.MeetingInput;

@Component
public class MeetingEntityMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(MeetingEntityMapper.class);

    public MeetingEntity map(MeetingInput input) {
        MeetingEntity entity = new MeetingEntity();
        entity.setSubject(input.getSubject());
        LOGGER.info("Mapping MeetingInput {} to MeetingEntity {}.", input, entity);

        return entity;
    }
}
