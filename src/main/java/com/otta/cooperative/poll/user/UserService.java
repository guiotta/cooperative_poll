package com.otta.cooperative.poll.user;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.otta.cooperative.poll.user.entity.UserEntity;
import com.otta.cooperative.poll.user.model.UserInput;
import com.otta.cooperative.poll.user.model.UserOutput;
import com.otta.cooperative.poll.user.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserOutput save(UserInput input) {
        UserEntity entity = map(input);
        entity = userRepository.save(entity);

        return map(entity);
    }

    public UserOutput getUserByDocument(String document) {
        Optional<UserEntity> optionalEntity = userRepository.findByDocument(document);

        if (optionalEntity.isPresent()) {
            return map(optionalEntity.get());
        }
        throw new IllegalArgumentException(String.format("Could not find any User with document %s.", document));
    }

    private UserEntity map(UserInput input) {
        String password = passwordEncoder.encode(input.getPassword());
        UserEntity entity = new UserEntity();
        entity.setName(input.getName());
        entity.setDocument(input.getDocument());
        entity.setPassword(password);
        entity.setEnabled(Boolean.TRUE);

        return entity;
    }
    private UserOutput map(UserEntity entity) {
        return new UserOutput(entity.getId(), entity.getDocument(), entity.getName());
    }

}
