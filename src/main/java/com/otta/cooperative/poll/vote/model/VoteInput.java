package com.otta.cooperative.poll.vote.model;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VoteInput {
    @JsonProperty("user")
    @NotNull
    private Long userId;
    @JsonProperty("poll")
    @NotNull
    private Long pollId;
    @JsonProperty("voteOption")
    @NotNull
    private Long voteOptionId;

    public VoteInput() { }

    public VoteInput(Long userId, Long pollId, Long voteOptionId) {
        this.userId = userId;
        this.pollId = pollId;
        this.voteOptionId = voteOptionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPollId() {
        return pollId;
    }

    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }

    public Long getVoteOptionId() {
        return voteOptionId;
    }

    public void setVoteOptionId(Long voteOptionId) {
        this.voteOptionId = voteOptionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pollId, userId, voteOptionId);
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
        return Objects.equals(pollId, other.pollId) && Objects.equals(userId, other.userId)
                && Objects.equals(voteOptionId, other.voteOptionId);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("VoteInput [userId=");
        builder.append(userId);
        builder.append(", pollId=");
        builder.append(pollId);
        builder.append(", voteOptionId=");
        builder.append(voteOptionId);
        builder.append("]");
        return builder.toString();
    }

}
