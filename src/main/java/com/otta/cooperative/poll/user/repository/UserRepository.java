package com.otta.cooperative.poll.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otta.cooperative.poll.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public Optional<UserEntity> findByDocument(String document);
}
