package com.otta.cooperative.poll.meeting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otta.cooperative.poll.meeting.entity.MeetingEntity;

public interface MeetingRepository extends JpaRepository<MeetingEntity, Long> {

}
