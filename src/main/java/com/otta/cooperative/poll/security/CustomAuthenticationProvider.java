package com.otta.cooperative.poll.security;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.otta.cooperative.poll.user.entity.UserEntity;
import com.otta.cooperative.poll.user.repository.UserRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String document = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<UserEntity> optionalUserEntity = userRepository.findByDocument(document);

        if (optionalUserEntity.isPresent()) {
            UserEntity entity = optionalUserEntity.get();

            if (passwordEncoder.matches(password, entity.getPassword())) {
                List<GrantedAuthority> authorities = entity.getRoles().stream()
                        .map(role -> role.getName())
                        .map(roleName -> new SimpleGrantedAuthority(roleName))
                        .collect(Collectors.toList());

                return new UsernamePasswordAuthenticationToken(document, password, authorities);
            }
        }

        throw new AuthenticationCredentialsNotFoundException("Could not sucessfully validate user credentials.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
