package com.otta.cooperative.poll.meeting.model.result;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultOutput {
    @JsonProperty("meeting")
    private Long meetingId;
    @JsonProperty("open")
    private Boolean open;
    @JsonProperty("close_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime closeDate;
    @JsonProperty("votes")
    private Collection<VoteOptionResultOutput> votes;
    @JsonProperty("message")
    private String message;

    public ResultOutput() { }

    public ResultOutput(Long meetingId, Boolean open, LocalDateTime closeDate, Collection<VoteOptionResultOutput> votes,
            String message) {
        this.meetingId = meetingId;
        this.open = open;
        this.closeDate = closeDate;
        this.votes = votes;
        this.message = message;
    }

    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public LocalDateTime getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDateTime closeDate) {
        this.closeDate = closeDate;
    }

    public Collection<VoteOptionResultOutput> getVotes() {
        return votes;
    }

    public void setVotes(Collection<VoteOptionResultOutput> votes) {
        this.votes = votes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int hashCode() {
        return Objects.hash(closeDate, meetingId, message, open, votes);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ResultOutput)) {
            return false;
        }
        ResultOutput other = (ResultOutput) obj;
        return Objects.equals(closeDate, other.closeDate) && Objects.equals(meetingId, other.meetingId)
                && Objects.equals(message, other.message) && Objects.equals(open, other.open)
                && Objects.equals(votes, other.votes);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ResultOutput [meetingId=");
        builder.append(meetingId);
        builder.append(", open=");
        builder.append(open);
        builder.append(", closeDate=");
        builder.append(closeDate);
        builder.append(", votes=");
        builder.append(votes);
        builder.append(", message=");
        builder.append(message);
        builder.append("]");
        return builder.toString();
    }

}
