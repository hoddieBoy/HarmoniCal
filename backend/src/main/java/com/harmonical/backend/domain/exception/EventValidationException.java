package com.harmonical.backend.domain.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventValidationException extends DomainException {
    private final ErrorCode errorCode;

    public enum ErrorCode {
        EMPTY_TITLE,
        NULL_TIMES,
        INVALID_TIMES,
        PAST_START_TIME
    }

    public EventValidationException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;

        Logger logger = LoggerFactory.getLogger(EventValidationException.class);
        if (logger.isErrorEnabled()) {
            logger.error(String.format("Event validation error: %s", message));
        }
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}