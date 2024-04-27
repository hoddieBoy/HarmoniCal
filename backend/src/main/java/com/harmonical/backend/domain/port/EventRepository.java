package com.harmonical.backend.domain.port;

import com.harmonical.backend.domain.model.Event;
public interface EventRepository {
    Event save(Event event);
}
