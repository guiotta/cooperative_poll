package com.otta.cooperative.poll.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.otta.cooperative.poll.user.entity.RoleEntity;
import com.otta.cooperative.poll.user.entity.UserEntity;
import com.otta.cooperative.poll.user.map.UserEntityMapper;
import com.otta.cooperative.poll.user.map.UserOutputMapper;
import com.otta.cooperative.poll.user.model.UserInput;
import com.otta.cooperative.poll.user.model.UserOutput;
import com.otta.cooperative.poll.user.repository.RoleRepository;
import com.otta.cooperative.poll.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private static final String SAVED_DOCUMENT = "100";
    private static final String UNKNOWN_DOCUMENT = "150";
    private static final String ROLE_NAME = "USER";

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserEntityMapper userEntityMapper;
    @Mock
    private UserOutputMapper userOutputMapper;

    @InjectMocks
    private UserService userService;

    @Mock
    private UserInput userInput;
    @Mock
    private UserEntity userEntity;
    @Mock
    private UserEntity savedUserEntity;
    @Mock
    private UserOutput userOutput;
    @Mock
    private Set<RoleEntity> roles;

    @Test
    public void shouldCorrectlySaveUserData() {
        // given
        given(roleRepository.findAllByName(ROLE_NAME)).willReturn(roles);
        given(userEntityMapper.map(userInput, roles)).willReturn(userEntity);
        given(userRepository.save(userEntity)).willReturn(savedUserEntity);
        given(userOutputMapper.map(savedUserEntity)).willReturn(userOutput);
        // when
        UserOutput actualValue = userService.save(userInput);
        // then
        assertEquals(userOutput, actualValue);
    }

    @Test
    public void shouldCorrectlyFindUserOutputByDocument() {
        // given
        given(userRepository.findByDocument(SAVED_DOCUMENT)).willReturn(Optional.of(savedUserEntity));
        given(userOutputMapper.map(savedUserEntity)).willReturn(userOutput);
        // when
        UserOutput actualValue = userService.getUserByDocument(SAVED_DOCUMENT);
        // then
        assertEquals(userOutput, actualValue);
    }

    @Test
    public void shouldThrowExceptionWhenServiceDontFindAnyUserByID() {
        // given
        given(userRepository.findByDocument(UNKNOWN_DOCUMENT)).willReturn(Optional.empty());
        // when
        assertThrows(IllegalArgumentException.class, () -> {
            userService.getUserByDocument(UNKNOWN_DOCUMENT);
        });
        // then
    }

}
