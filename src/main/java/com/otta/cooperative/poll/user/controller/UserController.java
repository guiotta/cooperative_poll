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

@RestController
@RequestMapping("/api/v1/user")
@Validated
public class UserController {
    private static final Logger lOGGER = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserOutput> saveUser(@Valid @RequestBody UserInput input) {
        lOGGER.info("Adding new user {} in database.", input);
        return ResponseEntity.ok(userService.save(input));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserOutput> getUserByDocument(@RequestParam("document") String document) {
        return ResponseEntity.ok(userService.getUserByDocument(document));
    }
}
