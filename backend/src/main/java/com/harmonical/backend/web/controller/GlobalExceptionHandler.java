package com.harmonical.backend.web.controller;

import com.harmonical.backend.domain.exception.InvalidDateFormatException;
import com.harmonical.backend.domain.exception.InvalidIDFormatException;
import com.harmonical.backend.domain.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        // Log exception details
        logger.error("An exception occurred: ", e);

        return ResponseEntity
                .internalServerError()
                .body(Map.of("message", "An error occurred. Please try again later."));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity
                .badRequest()
                .body(
                        Map.of(
                                "message", "Some fields are invalid",
                                "errors", errors
                        )
                );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(
                Map.of("message", e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(InvalidDateFormatException.class)
    public ResponseEntity<Map<String, String>> handleInvalidDateFormatException(InvalidDateFormatException e) {
        return ResponseEntity
                .badRequest()
                .body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(InvalidIDFormatException.class)
    public ResponseEntity<Map<String, String>> handleInvalidIDFormatException(InvalidIDFormatException e) {
        return ResponseEntity
                .badRequest()
                .body(Map.of("message", e.getMessage()));
    }
}
