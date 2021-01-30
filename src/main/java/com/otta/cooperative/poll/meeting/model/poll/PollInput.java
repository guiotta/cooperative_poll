package com.otta.cooperative.poll.meeting.model.poll;

import java.util.Objects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PollInput {
    @JsonProperty("meeting")
    @NotNull
    private Long meetingId;
    @JsonProperty("secondsToEndPoll")
    @NotNull
    @Min(value = 60l, message = "Poll duration cannot be less than 60 seconds.")
    private Long timeToEndInSeconds;

    public PollInput() { }

    public PollInput(@NotNull Long meetingId, @NotNull Long timeToEndInSeconds) {
        this.meetingId = meetingId;
        this.timeToEndInSeconds = timeToEndInSeconds;
    }

    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public Long getTimeToEndInSeconds() {
        return timeToEndInSeconds;
    }

    public void setTimeToEndInSeconds(Long timeToEndInSeconds) {
        this.timeToEndInSeconds = timeToEndInSeconds;
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingId, timeToEndInSeconds);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PollInput)) {
            return false;
        }
        PollInput other = (PollInput) obj;
        return Objects.equals(meetingId, other.meetingId)
                && Objects.equals(timeToEndInSeconds, other.timeToEndInSeconds);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PollInput [meetingId=");
        builder.append(meetingId);
        builder.append(", timeToEndInSeconds=");
        builder.append(timeToEndInSeconds);
        builder.append("]");
        return builder.toString();
    }
}
