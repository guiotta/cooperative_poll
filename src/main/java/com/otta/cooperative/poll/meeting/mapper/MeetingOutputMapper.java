package com.otta.cooperative.poll.meeting.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.otta.cooperative.poll.meeting.entity.MeetingEntity;
import com.otta.cooperative.poll.meeting.model.MeetingOutput;

@Component
public class MeetingOutputMapper {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MeetingOutputMapper.class);


    public MeetingOutput map(MeetingEntity entity) {
        MeetingOutput output = new MeetingOutput(entity.getId(), entity.getSubject());
        LOGGER.info("Mapping MeetingEntity {} to MeetingOutput {}.", entity, output);

        return output;
    }
}
