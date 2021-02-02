package com.otta.cooperative.poll.vote.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Collection;

import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.otta.cooperative.poll.vote.model.VoteInput;
import com.otta.cooperative.poll.vote.model.option.VoteOptionOutput;
import com.otta.cooperative.poll.vote.service.VoteService;

@ExtendWith(MockitoExtension.class)
public class VoteControllerTest {
    @InjectMocks
    private VoteController controller;

    @Mock
    private VoteService voteService;

    @Mock
    private VoteInput voteInput;
    @Mock
    private VoteOptionOutput voteOptionOutput;;

    @BeforeEach
    protected void setUp() {
    }

    @Test
    public void shouldCorrectlySaveVote() {
        // given
        // when
        controller.save(voteInput);
        // then
        verify(voteService).saveVote(voteInput);
    }

    @Test
    public void shouldCorrectlyListAllVoteOptions() {
        // given
        given(voteService.listVoteOptions()).willReturn(Lists.list(voteOptionOutput));
        // when
        ResponseEntity<Collection<VoteOptionOutput>> actualValue = controller.listAllVoteOptions();
        // then
        assertThat(actualValue.getBody(), Matchers.containsInAnyOrder(voteOptionOutput));
    }

}
