package com.otta.cooperative.poll.meeting.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "poll")
public class PollEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "open_date")
    private LocalDateTime open;
    @Column(name = "close_date")
    private LocalDateTime close;
    @Column(name = "enabled")
    private Boolean enabled;
    @OneToOne(mappedBy = "poll")
    private MeetingEntity meeting;

    public PollEntity() { }

    public PollEntity(Long id, LocalDateTime open, LocalDateTime close, Boolean enabled, MeetingEntity meeting) {
        this.id = id;
        this.open = open;
        this.close = close;
        this.enabled = enabled;
        this.meeting = meeting;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOpen() {
        return open;
    }

    public void setOpen(LocalDateTime open) {
        this.open = open;
    }

    public LocalDateTime getClose() {
        return close;
    }

    public void setClose(LocalDateTime close) {
        this.close = close;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public MeetingEntity getMeeting() {
        return meeting;
    }

    public void setMeeting(MeetingEntity meeting) {
        this.meeting = meeting;
    }

    @Override
    public int hashCode() {
        return Objects.hash(close, enabled, id, open);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PollEntity)) {
            return false;
        }
        PollEntity other = (PollEntity) obj;
        return Objects.equals(close, other.close) && Objects.equals(enabled, other.enabled)
                && Objects.equals(id, other.id) && Objects.equals(open, other.open);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PollEntity [id=");
        builder.append(id);
        builder.append(", open=");
        builder.append(open);
        builder.append(", close=");
        builder.append(close);
        builder.append(", enabled=");
        builder.append(enabled);
        builder.append("]");
        return builder.toString();
    }

}
