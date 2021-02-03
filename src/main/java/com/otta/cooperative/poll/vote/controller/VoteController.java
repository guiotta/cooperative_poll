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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/vote")
@Validated
public class VoteController {
    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @Operation(summary = "Adda vote to a specified Poll.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Vote added.",
                content = { @Content(mediaType = "application/json", schema = @Schema(implementation = VoteOutput.class)) }),
        @ApiResponse(responseCode = "422", description = "Vote have invalid information.",
                content = { @Content })
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteOutput> save(@Valid @RequestBody VoteInput input) {
        return ResponseEntity.ok(voteService.saveVote(input));
    }

    @Operation(summary = "Get vote options.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Found Vote Options.",
                content = { @Content(mediaType = "application/json", array =  @ArraySchema(schema = @Schema(implementation = VoteOptionOutput.class))) })
    })
    @GetMapping(path = "/option", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<VoteOptionOutput>> listAllVoteOptions() {
        return ResponseEntity.ok(voteService.listVoteOptions());
    }

}
