package com.otta.cooperative.poll.exception.model;

import java.time.LocalDateTime;

public class ExceptionOutput {
    private final LocalDateTime timestamp;
    private final int status;
    private final String message;
    private final String trace;
    private final String path;

    public ExceptionOutput(LocalDateTime timestamp, int status, String message, String trace, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.trace = trace;
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getTrace() {
        return trace;
    }

    public String getPath() {
        return path;
    }
}
