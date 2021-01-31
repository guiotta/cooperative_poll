package com.otta.cooperative.poll.meeting.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otta.cooperative.poll.meeting.entity.PollEntity;

public interface PollRepository extends JpaRepository<PollEntity, Long> {
    public Collection<PollEntity> findAllByEnabledTrue();

}
