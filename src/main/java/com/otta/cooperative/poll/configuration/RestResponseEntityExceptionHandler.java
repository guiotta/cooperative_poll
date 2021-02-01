package com.otta.cooperative.poll.configuration;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.otta.cooperative.poll.exception.model.ExceptionOutput;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @ExceptionHandler(value = JdbcSQLIntegrityConstraintViolationException.class)
    protected ResponseEntity<ExceptionOutput> handleSQLIntegrityConstraintViolationException(RuntimeException ex, WebRequest request) {
        LOGGER.error("An Error was ocorried processing an SQL Statement.", ex);

        final String errorMessage = "Could not execute an SQL Statement on database. Check data sent do Server.";
        final ExceptionOutput body = new ExceptionOutput(LocalDateTime.now(), HttpStatus.CONFLICT.value(),
                errorMessage, Arrays.toString(ex.getStackTrace()),
                ((ServletWebRequest) request).getRequest().getRequestURI().toString());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }
}
