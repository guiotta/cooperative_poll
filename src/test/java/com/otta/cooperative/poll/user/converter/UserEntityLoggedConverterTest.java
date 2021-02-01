package com.otta.cooperative.poll.user.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import com.otta.cooperative.poll.user.entity.UserEntity;
import com.otta.cooperative.poll.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserEntityLoggedConverterTest {
    private static final String DOCUMENT = "document";

    private UserEntityLoggedConverter converter;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityContext context;
    @Mock
    private Authentication authentication;
    @Mock
    private UserEntity userEntity;

    @BeforeEach
    protected void setUp() {
        this.converter = spy(new UserEntityLoggedConverter(userRepository));
        when(converter.getSecurityContext()).thenReturn(context);
        when(context.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(DOCUMENT);
        when(userRepository.findByDocument(DOCUMENT)).thenReturn(Optional.of(userEntity));
    }

    @Test
    public void shouldCorrectlyConvertUserInContextToUserEntity() {
        // given
        // when
        Optional<UserEntity> actualValue = converter.convert();
        // then
        assertEquals(userEntity, actualValue.get());
    }

}
