package com.otta.cooperative.poll.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otta.cooperative.poll.vote.entity.VoteEntity;

public interface VoteRepository extends JpaRepository<VoteEntity, Long> {

}
