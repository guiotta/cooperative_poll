package com.otta.cooperative.poll.vote.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vote_option")
public class VoteOptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;

    public VoteOptionEntity() { }

    public VoteOptionEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VoteOptionEntity)) {
            return false;
        }
        VoteOptionEntity other = (VoteOptionEntity) obj;
        return Objects.equals(id, other.id) && Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("VoteOption [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append("]");
        return builder.toString();
    }
}
