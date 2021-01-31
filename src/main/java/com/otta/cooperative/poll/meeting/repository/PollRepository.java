package com.otta.cooperative.poll.meeting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otta.cooperative.poll.meeting.entity.PollEntity;

public interface PollRepository extends JpaRepository<PollEntity, Long> {

}
