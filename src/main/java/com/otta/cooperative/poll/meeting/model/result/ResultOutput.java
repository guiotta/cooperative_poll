package com.otta.cooperative.poll.meeting.model.result;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

public class ResultOutput {
    private Long meetingId;
    private Boolean closed;
    private LocalDateTime closeDate;
    private Collection<VoteOptionResultOutput> votes;

    public ResultOutput() {
    }

    public ResultOutput(Long meetingId, Boolean closed, LocalDateTime closeDate,
            Collection<VoteOptionResultOutput> votes) {
        this.meetingId = meetingId;
        this.closed = closed;
        this.closeDate = closeDate;
        this.votes = votes;
    }

    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
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

    @Override
    public int hashCode() {
        return Objects.hash(closeDate, closed, meetingId, votes);
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
        return Objects.equals(closeDate, other.closeDate) && Objects.equals(closed, other.closed)
                && Objects.equals(meetingId, other.meetingId) && Objects.equals(votes, other.votes);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ResultOutput [meetingId=");
        builder.append(meetingId);
        builder.append(", closed=");
        builder.append(closed);
        builder.append(", closeDate=");
        builder.append(closeDate);
        builder.append(", votes=");
        builder.append(votes);
        builder.append("]");
        return builder.toString();
    }

}
