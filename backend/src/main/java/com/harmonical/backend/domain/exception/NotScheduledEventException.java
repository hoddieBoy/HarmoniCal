package com.harmonical.backend.domain.exception;

import com.harmonical.backend.domain.port.IEvent;

public class NotScheduledEventException extends IllegalArgumentException {

    public NotScheduledEventException(IEvent event) {
        super("Event with id " + event.getId() + " is not scheduled");
    }
}
