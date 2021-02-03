package com.otta.cooperative.poll.meeting.model;

import java.util.Objects;

public class MeetingOutput {
    private Long id;
    private String subject;

    public MeetingOutput() { }

    public MeetingOutput(Long id, String subject) {
        this.id = id;
        this.subject = subject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subject);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MeetingOutput)) {
            return false;
        }
        MeetingOutput other = (MeetingOutput) obj;
        return Objects.equals(id, other.id) && Objects.equals(subject, other.subject);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MeetingOutput [id=");
        builder.append(id);
        builder.append(", subject=");
        builder.append(subject);
        builder.append("]");
        return builder.toString();
    }

}
