package com.otta.cooperative.poll.meeting.model.result;

import java.util.Objects;

public class VoteOptionResultOutput {
    private Long id;
    private String label;
    private Long count;

    public VoteOptionResultOutput() {
    }

    public VoteOptionResultOutput(Long id, String label, Long count) {
        this.id = id;
        this.label = label;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, id, label);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VoteOptionResultOutput)) {
            return false;
        }
        VoteOptionResultOutput other = (VoteOptionResultOutput) obj;
        return Objects.equals(count, other.count) && Objects.equals(id, other.id) && Objects.equals(label, other.label);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("VoteOptionResultOutput [id=");
        builder.append(id);
        builder.append(", label=");
        builder.append(label);
        builder.append(", count=");
        builder.append(count);
        builder.append("]");
        return builder.toString();
    }

}
