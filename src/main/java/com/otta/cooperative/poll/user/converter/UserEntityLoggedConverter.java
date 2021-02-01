package com.otta.cooperative.poll.user.converter;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.otta.cooperative.poll.user.entity.UserEntity;
import com.otta.cooperative.poll.user.repository.UserRepository;

@Component
public class UserEntityLoggedConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserEntityLoggedConverter.class);

    private final UserRepository userRepository;

    public UserEntityLoggedConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserEntity> convert() {
        SecurityContext context = this.getSecurityContext();
        Authentication auth = context.getAuthentication();
        String document = new String(auth.getPrincipal().toString());

        LOGGER.debug("User logged with document {}.", document);

        return userRepository.findByDocument(document);
    }

    protected SecurityContext getSecurityContext() {
        LOGGER.trace("Getting Security Context from static method.");
        return SecurityContextHolder.getContext();
    }
}
