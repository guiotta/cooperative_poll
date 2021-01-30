package com.otta.cooperative.poll.meeting.model.poll;

import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PollOutput {
    private Long id;
    @JsonProperty("meeting")
    private Long meetingId;
    @JsonProperty("open_date_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime openPollDateTime;
    @JsonProperty("close_date_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime closePollDateTime;

    public PollOutput() { }

    public PollOutput(Long id, Long meetingId, LocalDateTime openPollDateTime, LocalDateTime closePollDateTime) {
        this.id = id;
        this.meetingId = meetingId;
        this.openPollDateTime = openPollDateTime;
        this.closePollDateTime = closePollDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public LocalDateTime getOpenPollDateTime() {
        return openPollDateTime;
    }

    public void setOpenPollDateTime(LocalDateTime openPollDateTime) {
        this.openPollDateTime = openPollDateTime;
    }

    public LocalDateTime getClosePollDateTime() {
        return closePollDateTime;
    }

    public void setClosePollDateTime(LocalDateTime closePollDateTime) {
        this.closePollDateTime = closePollDateTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(closePollDateTime, id, meetingId, openPollDateTime);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PollOutput)) {
            return false;
        }
        PollOutput other = (PollOutput) obj;
        return Objects.equals(closePollDateTime, other.closePollDateTime) && Objects.equals(id, other.id)
                && Objects.equals(meetingId, other.meetingId)
                && Objects.equals(openPollDateTime, other.openPollDateTime);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PollOutput [id=");
        builder.append(id);
        builder.append(", meetingId=");
        builder.append(meetingId);
        builder.append(", openPollDateTime=");
        builder.append(openPollDateTime);
        builder.append(", closePollDateTime=");
        builder.append(closePollDateTime);
        builder.append("]");
        return builder.toString();
    }

}
