package com.otta.cooperative.poll.user.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.otta.cooperative.poll.user.entity.UserEntity;
import com.otta.cooperative.poll.user.model.UserOutput;

@ExtendWith(MockitoExtension.class)
public class UserOutputMapperTest {
    private static final Long ID = Long.valueOf(5l);
    private static final String NAME = "NAME";
    private static final String DOCUMENT = "010";

    @InjectMocks
    private UserOutputMapper mapper;

    @Mock
    private UserEntity entity;

    @BeforeEach
    public void setUp() {
        given(entity.getId()).willReturn(ID);
        given(entity.getName()).willReturn(NAME);
        given(entity.getDocument()).willReturn(DOCUMENT);
    }

    @Test
    public void shouldCorrectlyMapUserEntityToUserOutput() {
        // given
        // when
        UserOutput actualValue = mapper.map(entity);
        // then
        assertEquals(ID, actualValue.getId());
        assertEquals(NAME, actualValue.getName());
        assertEquals(DOCUMENT, actualValue.getDocument());
    }

}
