package com.otta.cooperative.poll.vote.model;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VoteInput {
    @JsonProperty("meeting")
    @NotNull
    private Long meetingId;
    @JsonProperty("voteOption")
    @NotNull
    private Long voteOptionId;

    public VoteInput() { }

    public VoteInput(Long meetingId, Long voteOptionId) {
        this.meetingId = meetingId;
        this.voteOptionId = voteOptionId;
    }

    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long pollId) {
        this.meetingId = pollId;
    }

    public Long getVoteOptionId() {
        return voteOptionId;
    }

    public void setVoteOptionId(Long voteOptionId) {
        this.voteOptionId = voteOptionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingId, voteOptionId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VoteInput)) {
            return false;
        }
        VoteInput other = (VoteInput) obj;
        return Objects.equals(meetingId, other.meetingId) && Objects.equals(voteOptionId, other.voteOptionId);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("VoteInput [meetingId=");
        builder.append(meetingId);
        builder.append(", voteOptionId=");
        builder.append(voteOptionId);
        builder.append("]");
        return builder.toString();
    }

}
