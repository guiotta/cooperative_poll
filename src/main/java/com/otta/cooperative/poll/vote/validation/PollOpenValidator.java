package com.otta.cooperative.poll.vote.validation;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.otta.cooperative.poll.meeting.entity.PollEntity;

@Component
public class PollOpenValidator {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PollOpenValidator.class);


    public Boolean validate(PollEntity entity, LocalDateTime dateTimeToProcessVote) {
        LOGGER.debug("Validating vote process date {} with poll entity close date {} and poll entity enabled {}.",
                dateTimeToProcessVote, entity.getClose(), entity.getEnabled());
        
        return entity.getEnabled() && (entity.getClose() != null && entity.getClose().isAfter(dateTimeToProcessVote));
    }
}
