package com.otta.cooperative.poll.meeting.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "entity")
public class MeetingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "subject")
    private String subject;

    public MeetingEntity() { }

    public MeetingEntity(Long id, String subject) {
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
        if (!(obj instanceof MeetingEntity)) {
            return false;
        }
        MeetingEntity other = (MeetingEntity) obj;
        return Objects.equals(id, other.id) && Objects.equals(subject, other.subject);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MeetingEntity [id=");
        builder.append(id);
        builder.append(", subject=");
        builder.append(subject);
        builder.append("]");
        return builder.toString();
    }

}
