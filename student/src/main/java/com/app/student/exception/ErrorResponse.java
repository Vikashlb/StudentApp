package com.app.student.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
  private final LocalDateTime timeStamp;
  private final int statusCode;
  private final String errorMessage;

  public ErrorResponse(int statusCode, String errorMessage) {
    this.timeStamp = LocalDateTime.now();
    this.statusCode = statusCode;
    this.errorMessage = errorMessage;
  }

}
