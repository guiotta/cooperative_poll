package com.otta.cooperative.poll.vote.model.option;

import java.util.Objects;

public class VoteOptionOutput {
    private final Long id;
    private final String label;

    public VoteOptionOutput(Long id, String label) {
        this.id = id;
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VoteOptionOutput)) {
            return false;
        }
        VoteOptionOutput other = (VoteOptionOutput) obj;
        return Objects.equals(id, other.id) && Objects.equals(label, other.label);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("VoteOptionOutput [id=");
        builder.append(id);
        builder.append(", label=");
        builder.append(label);
        builder.append("]");
        return builder.toString();
    }

}
