package com.harmonical.backend.domain.exception;

public class InvalidIDFormatException extends RuntimeException {
    public InvalidIDFormatException(String message) {
        super(message);
    }
}
