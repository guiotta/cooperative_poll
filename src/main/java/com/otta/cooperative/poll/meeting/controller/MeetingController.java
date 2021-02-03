package com.otta.cooperative.poll.meeting.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.quartz.SchedulerException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.otta.cooperative.poll.meeting.model.MeetingInput;
import com.otta.cooperative.poll.meeting.model.MeetingOutput;
import com.otta.cooperative.poll.meeting.model.poll.PollInput;
import com.otta.cooperative.poll.meeting.model.poll.PollOutput;
import com.otta.cooperative.poll.meeting.model.result.ResultOutput;
import com.otta.cooperative.poll.meeting.service.MeetingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/meeting")
@Validated
public class MeetingController {
    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @Operation(summary = "Add a Meeting in system, without a Poll associated.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Meeting added.",
                content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MeetingOutput.class)) })
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MeetingOutput> saveMeeting(@Valid @RequestBody MeetingInput input) {
        return ResponseEntity.ok(meetingService.saveMeeting(input));
    }

    @Operation(summary = "Get all Meeting in system.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Found Meetings.",
                content = { @Content(mediaType = "application/json", array =  @ArraySchema(schema = @Schema(implementation = MeetingOutput.class))) })
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<MeetingOutput>> findAllMeetings(@RequestParam(name = "type", defaultValue = "all") String searchType) {
        return ResponseEntity.ok(meetingService.findAll(searchType));
    }

    @Operation(summary = "Add a Poll in system, associated with a Meeting.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Poll added.",
                content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PollOutput.class)) }),
        @ApiResponse(responseCode = "422", description = "Meeting already have a Poll or Meeting dont exists.",
                content = { @Content })
    })
    @PostMapping(value = "/poll", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PollOutput> savePoll(@Valid @RequestBody PollInput input) throws SchedulerException {
        return ResponseEntity.ok(meetingService.savePoll(input));
    }

    @Operation(summary = "Get result for a Poll.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Get a result.",
                content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ResultOutput.class)) }),
        @ApiResponse(responseCode = "422", description = "Could not find a Meeting or a Poll to find result.",
                content = { @Content })
    })
    @GetMapping(value = "/result", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultOutput> pollResult(@RequestParam(name = "meetingId") Long meetingId) {
        return ResponseEntity.ok(meetingService.generateResult(meetingId));
    }
}
