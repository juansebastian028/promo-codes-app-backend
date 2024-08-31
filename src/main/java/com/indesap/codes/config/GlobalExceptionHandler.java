package com.indesap.codes.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PromotionalCodeNotFoundException.class)
    public ResponseEntity<ApiError> handlePromotionalCodeNotFoundException(PromotionalCodeNotFoundException ex) {
        ApiError error = new ApiError(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
