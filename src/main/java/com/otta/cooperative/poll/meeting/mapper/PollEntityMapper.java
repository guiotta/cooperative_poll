package com.otta.cooperative.poll.meeting.mapper;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.otta.cooperative.poll.meeting.entity.MeetingEntity;
import com.otta.cooperative.poll.meeting.entity.PollEntity;
import com.otta.cooperative.poll.meeting.model.poll.PollInput;

@Component
public class PollEntityMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(PollEntityMapper.class);


    public PollEntity map(PollInput pollInput, MeetingEntity meetingEntity) {
        PollEntity pollEntity = new PollEntity();
        LocalDateTime pollStartDateTime = this.getLocalDateTimeNow();
        Boolean isEnabled = Boolean.TRUE;
        LocalDateTime pollEndDateTime = pollStartDateTime.plusSeconds(pollInput.getTimeToEndInSeconds());

        pollEntity.setOpen(pollStartDateTime);
        pollEntity.setClose(pollEndDateTime);
        pollEntity.setEnabled(isEnabled);
        pollEntity.setMeeting(meetingEntity);

        LOGGER.info("Mapping PollInput {} and MeetingEntity {} to PollEntity {}.", pollInput, meetingEntity, pollEntity);

        return pollEntity;
    }

    /**
     * Método criado com a intenção de simplificar a criação dos testes unitários.
     */
    protected LocalDateTime getLocalDateTimeNow() {
        return LocalDateTime.now();
    }
}
