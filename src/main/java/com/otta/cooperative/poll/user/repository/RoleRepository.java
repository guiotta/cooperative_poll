package com.otta.cooperative.poll.user.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otta.cooperative.poll.user.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    public Set<RoleEntity> findAllByName(String name);
}
