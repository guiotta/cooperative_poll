package com.otta.cooperative.poll.meeting.model;

import java.util.Objects;

import javax.validation.constraints.NotBlank;

public class MeetingInput {
    @NotBlank
    private String subject;

    public MeetingInput() { }

    public MeetingInput(@NotBlank String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MeetingInput)) {
            return false;
        }
        MeetingInput other = (MeetingInput) obj;
        return Objects.equals(subject, other.subject);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MeetingInput [subject=");
        builder.append(subject);
        builder.append("]");
        return builder.toString();
    }

}
