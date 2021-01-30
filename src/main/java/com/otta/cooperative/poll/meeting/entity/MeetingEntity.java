package com.otta.cooperative.poll.meeting.entity;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "meeting")
public class MeetingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "subject")
    private String subject;
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_id", referencedColumnName = "id")
    private PollEntity poll;

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

    public PollEntity getPoll() {
        return poll;
    }

    public void setPoll(PollEntity poll) {
        this.poll = poll;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, poll, subject);
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
        return Objects.equals(id, other.id) && Objects.equals(poll, other.poll)
                && Objects.equals(subject, other.subject);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MeetingEntity [id=");
        builder.append(id);
        builder.append(", subject=");
        builder.append(subject);
        builder.append(", poll=");
        builder.append(poll);
        builder.append("]");
        return builder.toString();
    }

}
