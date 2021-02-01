package com.otta.cooperative.poll.meeting.extractor;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.otta.cooperative.poll.meeting.entity.MeetingEntity;
import com.otta.cooperative.poll.meeting.entity.PollEntity;

@Component
public class PollEntityExtractor {
    private static final Logger LOGGER = LoggerFactory.getLogger(PollEntityExtractor.class);

    public Optional<PollEntity> extract(Optional<MeetingEntity> optionalMeetingEntity) {
        if (optionalMeetingEntity.isPresent()) {
            MeetingEntity meetingEntity = optionalMeetingEntity.get();
            LOGGER.info("Extracting PollEntity from MeetingEntity {}.", meetingEntity);

            return Optional.ofNullable(meetingEntity.getPoll());
        }

        LOGGER.debug("Could not find a MeetingEntity in Optional.");
        return Optional.empty();
    }
}
