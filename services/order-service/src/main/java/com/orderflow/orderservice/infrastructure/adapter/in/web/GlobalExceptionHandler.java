package com.orderflow.orderservice.infrastructure.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice 
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException exception){
        String message = exception.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .findFirst()
                    .map(error -> error.getField() 
                    + ": " + error.getDefaultMessage())
                    .orElse("Invalid request");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("VALIDATION_ERROR", message));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalState(IllegalStateException exception){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("ILLEGAL_STATE", exception.getMessage()));
    }
}
