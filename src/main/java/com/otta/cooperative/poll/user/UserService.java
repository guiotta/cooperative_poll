package com.otta.cooperative.poll.user;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.otta.cooperative.poll.user.entity.UserEntity;
import com.otta.cooperative.poll.user.map.UserEntityMapper;
import com.otta.cooperative.poll.user.map.UserOutputMapper;
import com.otta.cooperative.poll.user.model.UserInput;
import com.otta.cooperative.poll.user.model.UserOutput;
import com.otta.cooperative.poll.user.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;
    private final UserOutputMapper userOutputMapper;

    public UserService(UserRepository userRepository, UserEntityMapper userEntityMapper,
            UserOutputMapper userOutputMapper) {
        this.userRepository = userRepository;
        this.userEntityMapper = userEntityMapper;
        this.userOutputMapper = userOutputMapper;
    }

    public UserOutput save(UserInput input) {
        UserEntity entity = userEntityMapper.map(input);
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
