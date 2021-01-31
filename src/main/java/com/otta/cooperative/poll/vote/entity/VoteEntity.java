package com.otta.cooperative.poll.vote.entity;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.otta.cooperative.poll.meeting.entity.PollEntity;
import com.otta.cooperative.poll.user.entity.UserEntity;

@Entity
@Table(name = "vote")
public class VoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_id", referencedColumnName = "id", insertable = false, updatable = false)
    private PollEntity pollEntity;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_option_id", referencedColumnName = "id", insertable = false, updatable = false)
    private VoteOptionEntity voteOptionEntity;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserEntity userEntity;

    public VoteEntity() { }

    public VoteEntity(Long id, PollEntity pollEntity, VoteOptionEntity voteOptionEntity, UserEntity userEntity) {
        this.id = id;
        this.pollEntity = pollEntity;
        this.voteOptionEntity = voteOptionEntity;
        this.userEntity = userEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PollEntity getPollEntity() {
        return pollEntity;
    }

    public void setPollEntity(PollEntity pollEntity) {
        this.pollEntity = pollEntity;
    }

    public VoteOptionEntity getVoteOptionEntity() {
        return voteOptionEntity;
    }

    public void setVoteOptionEntity(VoteOptionEntity voteOptionEntity) {
        this.voteOptionEntity = voteOptionEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pollEntity, userEntity, voteOptionEntity);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VoteEntity)) {
            return false;
        }
        VoteEntity other = (VoteEntity) obj;
        return Objects.equals(id, other.id) && Objects.equals(pollEntity, other.pollEntity)
                && Objects.equals(userEntity, other.userEntity)
                && Objects.equals(voteOptionEntity, other.voteOptionEntity);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("VoteEntity [id=");
        builder.append(id);
        builder.append(", voteOptionEntity=");
        builder.append(voteOptionEntity);
        builder.append("]");
        return builder.toString();
    }
}
