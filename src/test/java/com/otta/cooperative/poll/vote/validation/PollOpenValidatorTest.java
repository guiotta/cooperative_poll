package com.otta.cooperative.poll.vote.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.otta.cooperative.poll.meeting.entity.PollEntity;

@ExtendWith(MockitoExtension.class)
public class PollOpenValidatorTest {
    private static final String CLOSE_DATE = "2021-01-31 12:00:00";
    private static final String BEFORE_CLOSE_DATE = "2021-01-31 11:59:59";
    private static final String AFTER_CLOSE_DATE = "2021-01-31 12:00:01";
    @InjectMocks
    private PollOpenValidator validator;

    @Mock
    private PollEntity pollEntity;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @BeforeEach
    protected void setUp() {
    }

    @Test
    public void shouldValidatePollEntityWhenItIsEnabledAndCloseIsAfterDateToValidate() {
        // given
        LocalDateTime close = LocalDateTime.parse(CLOSE_DATE, formatter);
        LocalDateTime dateTimeToValidate = LocalDateTime.parse(BEFORE_CLOSE_DATE, formatter);
        given(pollEntity.getEnabled()).willReturn(Boolean.TRUE);
        given(pollEntity.getClose()).willReturn(close);
        // when
        Boolean actualValue = validator.validate(pollEntity, dateTimeToValidate);
        // then
        assertTrue(actualValue);
    }

    @Test
    public void shouldNotValidatePollEntityWhenItIsEnabledAndCloseIsBeforeDateToValidate() {
        // given
        LocalDateTime close = LocalDateTime.parse(CLOSE_DATE, formatter);
        LocalDateTime dateTimeToValidate = LocalDateTime.parse(AFTER_CLOSE_DATE, formatter);
        given(pollEntity.getEnabled()).willReturn(Boolean.TRUE);
        given(pollEntity.getClose()).willReturn(close);
        // when
        Boolean actualValue = validator.validate(pollEntity, dateTimeToValidate);
        // then
        assertFalse(actualValue);
    }

    @Test
    public void shouldNotValidatePollEntityWhenItIsNotEnabled() {
        // given
        LocalDateTime dateTimeToValidate = LocalDateTime.parse(BEFORE_CLOSE_DATE, formatter);
        given(pollEntity.getEnabled()).willReturn(Boolean.FALSE);
        // when
        Boolean actualValue = validator.validate(pollEntity, dateTimeToValidate);
        // then
        assertFalse(actualValue);
    }

}
