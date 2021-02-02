package com.otta.cooperative.poll.meeting.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.otta.cooperative.poll.meeting.entity.MeetingEntity;

public interface MeetingRepository extends JpaRepository<MeetingEntity, Long> {
    @Query("SELECT m from MeetingEntity m WHERE m.poll.close > CURRENT_TIMESTAMP")
    Collection<MeetingEntity> findByPollCloseAfterNow();
}
