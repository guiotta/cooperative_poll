package com.otta.cooperative.poll.user.converter;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.otta.cooperative.poll.user.entity.UserEntity;
import com.otta.cooperative.poll.user.repository.UserRepository;

@Component
public class UserEntityLoggedConverter {
    private final UserRepository userRepository;

    public UserEntityLoggedConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserEntity> convert() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String document = new String(auth.getPrincipal().toString());

        return userRepository.findByDocument(document);
    }
}
