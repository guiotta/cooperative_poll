package com.otta.cooperative.poll.vote.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otta.cooperative.poll.vote.model.VoteInput;
import com.otta.cooperative.poll.vote.model.VoteOutput;
import com.otta.cooperative.poll.vote.model.option.VoteOptionOutput;
import com.otta.cooperative.poll.vote.service.VoteService;

@RestController
@RequestMapping("/api/v1/vote")
@Validated
public class VoteController {
    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteOutput> save(@Valid @RequestBody VoteInput input) {
        return ResponseEntity.ok(voteService.saveVote(input));
    }

    @GetMapping(path = "/option", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<VoteOptionOutput>> listAllVOteOptions() {
        return ResponseEntity.ok(voteService.listVoteOptions());
    }

}
