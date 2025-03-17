package com.app.student.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {InvalidDataException.class, DataUnavailableException.class, DuplicateDataException.class})
    public ResponseEntity<ErrorResponse> handleGlobalException(RuntimeException ex){
        HttpStatus httpStatus = (ex instanceof InvalidDataException)?HttpStatus.BAD_REQUEST:(ex instanceof DataUnavailableException)?HttpStatus.NOT_FOUND:HttpStatus.BAD_REQUEST;
//        String error = "Invalid Data";
        ErrorResponse response = new ErrorResponse(httpStatus.value(), ex.getMessage());
        return new ResponseEntity<>(response, httpStatus);
    }

   /* @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String errorMessage = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        ErrorResponse response = new ErrorResponse(httpStatus.value(), errorMessage);
        return new ResponseEntity<>(response, httpStatus);
    } */

}
