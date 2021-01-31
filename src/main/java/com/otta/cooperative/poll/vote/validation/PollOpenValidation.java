package com.otta.cooperative.poll.vote.validation;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.otta.cooperative.poll.meeting.entity.PollEntity;

@Component
public class PollOpenValidation {

    public Boolean validate(PollEntity entity, LocalDateTime dateTimeToProcessVote) {
        return entity.getEnabled() && (entity.getClose() != null && entity.getClose().isAfter(dateTimeToProcessVote));
    }
}
