package com.otta.cooperative.poll.vote.model;

import java.util.Objects;

public class VoteOutput {
    private Long id;
    private Long voteOptionId;
    private String voteOptionLabel;

    public VoteOutput() {
    }

    public VoteOutput(Long id, Long voteOptionId, String voteOptionLabel) {
        this.id = id;
        this.voteOptionId = voteOptionId;
        this.voteOptionLabel = voteOptionLabel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVoteOptionId() {
        return voteOptionId;
    }

    public void setVoteOptionId(Long voteOptionId) {
        this.voteOptionId = voteOptionId;
    }

    public String getVoteOptionLabel() {
        return voteOptionLabel;
    }

    public void setVoteOptionLabel(String voteOptionLabel) {
        this.voteOptionLabel = voteOptionLabel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, voteOptionId, voteOptionLabel);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VoteOutput)) {
            return false;
        }
        VoteOutput other = (VoteOutput) obj;
        return Objects.equals(id, other.id) && Objects.equals(voteOptionId, other.voteOptionId)
                && Objects.equals(voteOptionLabel, other.voteOptionLabel);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("VoteOutput [id=");
        builder.append(id);
        builder.append(", voteOptionId=");
        builder.append(voteOptionId);
        builder.append(", voteOptionLabel=");
        builder.append(voteOptionLabel);
        builder.append("]");
        return builder.toString();
    }

}
