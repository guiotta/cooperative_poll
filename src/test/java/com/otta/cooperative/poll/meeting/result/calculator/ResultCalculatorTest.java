package com.otta.cooperative.poll.meeting.result.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.otta.cooperative.poll.meeting.model.result.VoteOptionResultOutput;

@ExtendWith(MockitoExtension.class)
public class ResultCalculatorTest {
    private static final String B = "B";
    private static final String C = "C";
    private static final Long ZERO = 0l;
    private static final Long ONE = 1l;
    private static final Long TWO = 2l;

    @InjectMocks
    private ResultCalculator calculator;

    @Mock
    private VoteOptionResultOutput outputA;
    @Mock
    private VoteOptionResultOutput outputB;
    @Mock
    private VoteOptionResultOutput outputC;

    @Test
    public void shouldCorrectlyCalculateWinner() {
        // given
        given(outputA.getCount()).willReturn(ZERO);
        given(outputB.getCount()).willReturn(ONE);
        given(outputB.getLabel()).willReturn(B);
        given(outputC.getCount()).willReturn(ZERO);
        // when
        String actualResult = calculator.calculate(Lists.list(outputA, outputB, outputC));
        // then
        assertEquals("WINNER B", actualResult);
    }

    @Test
    public void shouldCorrectlyCalculateWinnerWhenAllOptionsAreUnique() {
        // given
        given(outputA.getCount()).willReturn(ZERO);
        given(outputB.getCount()).willReturn(ONE);
        given(outputC.getCount()).willReturn(TWO);
        given(outputC.getLabel()).willReturn(C);
        // when
        String actualResult = calculator.calculate(Lists.list(outputA, outputB, outputC));
        // then
        assertEquals("WINNER C", actualResult);
    }

    @Test
    public void shouldCorrectlyCalculateDrawWhenNoOneGetVotes() {
        // given
        given(outputA.getCount()).willReturn(ZERO);
        given(outputB.getCount()).willReturn(ZERO);
        given(outputC.getCount()).willReturn(ZERO);
        // when
        String actualResult = calculator.calculate(Lists.list(outputA, outputB, outputC));
        // then
        assertEquals("DRAW", actualResult);
    }

    @Test
    public void shouldCorrectlyCalculateDrawWhenAllOptionsGetVotes() {
        // given
        given(outputA.getCount()).willReturn(ONE);
        given(outputB.getCount()).willReturn(ONE);
        given(outputC.getCount()).willReturn(ONE);
        // when
        String actualResult = calculator.calculate(Lists.list(outputA, outputB, outputC));
        // then
        assertEquals("DRAW", actualResult);
    }

}
