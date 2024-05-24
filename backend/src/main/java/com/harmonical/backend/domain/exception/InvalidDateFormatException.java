package com.harmonical.backend.domain.exception;

public class InvalidDateFormatException extends IllegalArgumentException {
    public InvalidDateFormatException(String message) {
        super(message);
    }
}
