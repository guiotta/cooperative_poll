package com.otta.cooperative.poll.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otta.cooperative.poll.vote.entity.VoteOptionEntity;

public interface VoteOptionRepository extends JpaRepository<VoteOptionEntity, Long> {

}
