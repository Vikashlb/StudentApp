package com.app.student.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {StudentNotFoundException.class, DuplicateRecordException.class})
    public ResponseEntity<ErrorResponse> handleGlobalException(RuntimeException ex){
        HttpStatus httpStatus = (ex instanceof StudentNotFoundException)?HttpStatus.NOT_FOUND:HttpStatus.CONFLICT;
      //String error = "Invalid Data";
        ErrorResponse response = new ErrorResponse(httpStatus.value(), ex.getMessage());
        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage()) // Field name + error message
                .findFirst()
                .orElse("Validation failed");
        ErrorResponse response = new ErrorResponse(httpStatus.value(), errorMessage);
        return new ResponseEntity<>(response, httpStatus);
    }

}
