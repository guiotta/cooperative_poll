package com.otta.cooperative.poll.user.map;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.otta.cooperative.poll.user.entity.RoleEntity;
import com.otta.cooperative.poll.user.entity.UserEntity;
import com.otta.cooperative.poll.user.model.UserInput;

@Component
public class UserEntityMapper {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UserEntityMapper.class);

    private final PasswordEncoder passwordEncoder;

    public UserEntityMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity map(UserInput input, Set<RoleEntity> roles) {
        String password = passwordEncoder.encode(input.getPassword());
        UserEntity entity = new UserEntity();
        entity.setName(input.getName());
        entity.setDocument(input.getDocument());
        entity.setPassword(password);
        entity.setEnabled(Boolean.TRUE);
        entity.setRoles(roles);

        LOGGER.info("Mapping UserInput {} to UserEntity {}", input, entity);

        return entity;
    }
}
