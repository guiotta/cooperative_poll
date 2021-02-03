package com.otta.cooperative.poll.vote.client.model;

import java.util.Objects;

public class StatusResource {
    private String status;

    public StatusResource() { }

    public StatusResource(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(status);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StatusResource)) {
            return false;
        }
        StatusResource other = (StatusResource) obj;
        return Objects.equals(status, other.status);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("StatusResource [status=");
        builder.append(status);
        builder.append("]");
        return builder.toString();
    }

}
