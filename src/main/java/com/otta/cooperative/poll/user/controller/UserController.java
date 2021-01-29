package com.otta.cooperative.poll.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otta.cooperative.poll.user.model.UserInput;

@RestController
@RequestMapping("/api/v1/user")
@Validated
public class UserController {
    private static final Logger lOGGER = LoggerFactory.getLogger(UserController.class);


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void saveUser(@RequestBody UserInput input) {
        lOGGER.info("Adding new user {} in database.", input);
    }
}
