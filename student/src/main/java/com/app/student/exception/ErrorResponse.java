package com.app.student.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
    private final LocalDateTime timeStamp;
    private final int statusCode;
    private final String errorMessage;

    public ErrorResponse(int statusCode, String errorMessage) {
        this.timeStamp = LocalDateTime.now();
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
