package com.otta.cooperative.poll.user.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.otta.cooperative.poll.user.entity.RoleEntity;
import com.otta.cooperative.poll.user.entity.UserEntity;
import com.otta.cooperative.poll.user.map.UserEntityMapper;
import com.otta.cooperative.poll.user.map.UserOutputMapper;
import com.otta.cooperative.poll.user.model.UserInput;
import com.otta.cooperative.poll.user.model.UserOutput;
import com.otta.cooperative.poll.user.repository.RoleRepository;
import com.otta.cooperative.poll.user.repository.UserRepository;

@Service
public class UserService {
    private static final String ROLE_NAME = "USER";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserEntityMapper userEntityMapper;
    private final UserOutputMapper userOutputMapper;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserEntityMapper userEntityMapper,
            UserOutputMapper userOutputMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userEntityMapper = userEntityMapper;
        this.userOutputMapper = userOutputMapper;
    }

    public UserOutput save(UserInput input) {
        Set<RoleEntity> roles = roleRepository.findAllByName(ROLE_NAME);
        UserEntity entity = userEntityMapper.map(input, roles);
        entity = userRepository.save(entity);

        return userOutputMapper.map(entity);
    }

    public UserOutput getUserByDocument(String document) {
        Optional<UserEntity> optionalEntity = userRepository.findByDocument(document);

        if (optionalEntity.isPresent()) {
            return userOutputMapper.map(optionalEntity.get());
        }
        throw new IllegalArgumentException(String.format("Could not find any User with document %s.", document));
    }

}
