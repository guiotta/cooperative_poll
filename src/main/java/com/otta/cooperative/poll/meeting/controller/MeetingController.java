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
import org.springframework.web.bind.annotation.RestController;

import com.otta.cooperative.poll.meeting.model.MeetingInput;
import com.otta.cooperative.poll.meeting.model.MeetingOutput;
import com.otta.cooperative.poll.meeting.model.poll.PollInput;
import com.otta.cooperative.poll.meeting.model.poll.PollOutput;
import com.otta.cooperative.poll.meeting.model.result.ResultOutput;
import com.otta.cooperative.poll.meeting.service.MeetingService;

@RestController
@RequestMapping("/api/v1/meeting")
@Validated
public class MeetingController {
    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MeetingOutput> saveMeeting(@Valid @RequestBody MeetingInput input) {
        return ResponseEntity.ok(meetingService.saveMeeting(input));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<MeetingOutput>> findAll() {
        return ResponseEntity.ok(meetingService.findAll());
    }

    @PostMapping(value = "/poll", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PollOutput> savePoll(@Valid @RequestBody PollInput input) throws SchedulerException {
        return ResponseEntity.ok(meetingService.savePoll(input));
    }

    @GetMapping(value = "/result", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultOutput> pollResult() {
        return ResponseEntity.ok(meetingService.generateResult(1l));
    }
}
