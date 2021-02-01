package com.otta.cooperative.poll.vote.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.otta.cooperative.poll.meeting.entity.PollEntity;
import com.otta.cooperative.poll.meeting.repository.PollRepository;
import com.otta.cooperative.poll.user.converter.UserEntityLoggedConverter;
import com.otta.cooperative.poll.user.entity.UserEntity;
import com.otta.cooperative.poll.vote.entity.VoteEntity;
import com.otta.cooperative.poll.vote.entity.VoteOptionEntity;
import com.otta.cooperative.poll.vote.mapper.VoteEntityMapper;
import com.otta.cooperative.poll.vote.mapper.VoteOutputMapper;
import com.otta.cooperative.poll.vote.model.VoteInput;
import com.otta.cooperative.poll.vote.model.VoteOutput;
import com.otta.cooperative.poll.vote.repository.VoteOptionRepository;
import com.otta.cooperative.poll.vote.repository.VoteRepository;
import com.otta.cooperative.poll.vote.validation.PollOpenValidator;

@ExtendWith(MockitoExtension.class)
public class VoteServiceTest {
    private static final Long VOTE_OPTION_ID = 10l;
    private static final Long POLL_ID = 20l;

    private VoteService voteService;

    @Mock
    private VoteRepository voteRepository;
    @Mock
    private VoteOptionRepository voteOptionRepository;
    @Mock
    private PollRepository pollRepository;
    @Mock
    private PollOpenValidator pollOpenValidation;
    @Mock
    private UserEntityLoggedConverter userEntityLoggedConverter;
    @Mock
    private VoteOutputMapper voteOutputMapper;
    @Mock
    private VoteEntityMapper voteEntityMapper;

    @Mock
    private VoteInput voteInput;
    @Mock
    private VoteOptionEntity voteOptionEntity;
    @Mock
    private UserEntity userEntity;
    @Mock
    private PollEntity pollEntity;
    @Mock
    private VoteEntity voteEntity;
    @Mock
    private VoteEntity voteEntitySaved;
    @Mock
    private VoteOutput voteOutput;

    private LocalDateTime now;

    @BeforeEach
    protected void setUp() {
        voteService = spy(new VoteService(voteRepository, voteOptionRepository, pollRepository, pollOpenValidation,
                userEntityLoggedConverter, voteOutputMapper, voteEntityMapper));
        now = LocalDateTime.now();

        when(voteService.getLocalDateTimeNow()).thenReturn(now);
        when(voteInput.getVoteOptionId()).thenReturn(VOTE_OPTION_ID);
        when(voteInput.getPollId()).thenReturn(POLL_ID);
    }

    @Test
    public void shouldCorrectlySaveVote() {
        // given
        when(voteOptionRepository.findById(VOTE_OPTION_ID)).thenReturn(Optional.of(voteOptionEntity));
        when(userEntityLoggedConverter.convert()).thenReturn(Optional.of(userEntity));
        when(pollRepository.findById(POLL_ID)).thenReturn(Optional.of(pollEntity));
        when(pollOpenValidation.validate(pollEntity, now)).thenReturn(Boolean.TRUE);
        when(voteEntityMapper.map(pollEntity, userEntity, voteOptionEntity)).thenReturn(voteEntity);
        when(voteRepository.save(voteEntity)).thenReturn(voteEntitySaved);
        when(voteOutputMapper.map(voteEntitySaved)).thenReturn(voteOutput);
        // when
        VoteOutput actualValue = voteService.saveVote(voteInput);
        // then
        assertEquals(voteOutput, actualValue);
    }

    @Test
    public void shouldThrowExceptionWhenPollIsClosed() {
        // given
        when(voteOptionRepository.findById(VOTE_OPTION_ID)).thenReturn(Optional.of(voteOptionEntity));
        when(userEntityLoggedConverter.convert()).thenReturn(Optional.of(userEntity));
        when(pollRepository.findById(POLL_ID)).thenReturn(Optional.of(pollEntity));
        when(pollOpenValidation.validate(pollEntity, now)).thenReturn(Boolean.FALSE);
        // when
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            voteService.saveVote(voteInput);
        });
        // then
    }

    @Test
    public void shouldThrowExceptionWhenOptionalVoteOptionEntityIsEmpty() {
        // given
        when(voteOptionRepository.findById(VOTE_OPTION_ID)).thenReturn(Optional.empty());
        when(userEntityLoggedConverter.convert()).thenReturn(Optional.of(userEntity));
        when(pollRepository.findById(POLL_ID)).thenReturn(Optional.of(pollEntity));
        // when
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            voteService.saveVote(voteInput);
        });
        // then
    }

    @Test
    public void shouldThrowExceptionWhenOptionalUserEntityIsEmpty() {
        // given
        when(voteOptionRepository.findById(VOTE_OPTION_ID)).thenReturn(Optional.of(voteOptionEntity));
        when(userEntityLoggedConverter.convert()).thenReturn(Optional.empty());
        when(pollRepository.findById(POLL_ID)).thenReturn(Optional.of(pollEntity));
        // when
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            voteService.saveVote(voteInput);
        });
        // then
    }

    @Test
    public void shouldThrowExceptionWhenOptionalPollEntityIsEmpty() {
        // given
        when(voteOptionRepository.findById(VOTE_OPTION_ID)).thenReturn(Optional.of(voteOptionEntity));
        when(userEntityLoggedConverter.convert()).thenReturn(Optional.of(userEntity));
        when(pollRepository.findById(POLL_ID)).thenReturn(Optional.empty());
        // when
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            voteService.saveVote(voteInput);
        });
        // then
    }
}
