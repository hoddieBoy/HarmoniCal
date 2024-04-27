package com.harmonical.backend.domain.services;

import com.harmonical.backend.domain.model.Event;
import com.harmonical.backend.domain.port.EventRepository;
import org.springframework.stereotype.Service;

public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }
}
