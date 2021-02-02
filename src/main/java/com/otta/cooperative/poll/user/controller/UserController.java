package com.otta.cooperative.poll.user.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.otta.cooperative.poll.user.model.UserInput;
import com.otta.cooperative.poll.user.model.UserOutput;
import com.otta.cooperative.poll.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/user")
@Validated
public class UserController {
    private static final Logger lOGGER = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Add a User to system.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User added.",
                content = { @Content(mediaType = "aaplication/json", schema = @Schema(implementation = UserOutput.class)) })
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserOutput> saveUser(@Valid @RequestBody UserInput input) {
        lOGGER.info("Adding new user {} in database.", input);
        return ResponseEntity.ok(userService.save(input));
    }

    @Operation(summary = "Find a User by its document.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Found the User.",
                content = { @Content(mediaType = "aaplication/json", schema = @Schema(implementation = UserOutput.class)) }),
        @ApiResponse(responseCode = "422", description = "Unknown document sent to search.",
        content = { @Content })
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserOutput> getUserByDocument(@RequestParam("document") String document) {
        return ResponseEntity.ok(userService.getUserByDocument(document));
    }
}
