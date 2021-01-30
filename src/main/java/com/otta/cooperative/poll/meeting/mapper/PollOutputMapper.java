package com.otta.cooperative.poll.meeting.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.otta.cooperative.poll.meeting.entity.PollEntity;
import com.otta.cooperative.poll.meeting.model.poll.PollOutput;

@Component
public class PollOutputMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(PollOutputMapper.class);


    public PollOutput map(PollEntity entity) {
        PollOutput output = new PollOutput(entity.getId(), entity.getMeeting().getId(), entity.getOpen(), entity.getClose());
        LOGGER.info("Mapping PollEntity {} to PollOutput {}.", entity, output);

        return output;
    }
}
