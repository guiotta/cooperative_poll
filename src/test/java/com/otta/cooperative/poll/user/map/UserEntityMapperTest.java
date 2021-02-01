package com.otta.cooperative.poll.user.map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import org.assertj.core.util.Sets;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.otta.cooperative.poll.user.entity.RoleEntity;
import com.otta.cooperative.poll.user.entity.UserEntity;
import com.otta.cooperative.poll.user.model.UserInput;

@ExtendWith(MockitoExtension.class)
public class UserEntityMapperTest {
    private static final String NAME = "name";
    private static final String DOCUMENT = "01";
    private static final String PASSWORD = "password";
    private static final String ENCODED_PASSWORD = "encoded";

    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserEntityMapper mapper;

    @Mock
    private UserInput input;
    @Mock
    private RoleEntity role;

    @BeforeEach
    public void setUp() {
        given(input.getName()).willReturn(NAME);
        given(input.getDocument()).willReturn(DOCUMENT);
        given(input.getPassword()).willReturn(PASSWORD);
        given(passwordEncoder.encode(PASSWORD)).willReturn(ENCODED_PASSWORD);
    }

    @Test
    public void shouldCorrectlyMapUserInputToUserEntity() {
        // given
        // when
        UserEntity actualValue = mapper.map(input, Sets.newLinkedHashSet(role));
        // then
        assertEquals(NAME, actualValue.getName());
        assertEquals(DOCUMENT, actualValue.getDocument());
        assertEquals(ENCODED_PASSWORD, actualValue.getPassword());
        assertThat(actualValue.getRoles(), Matchers.containsInAnyOrder(role));
        assertTrue(actualValue.isEnabled());
    }

}
