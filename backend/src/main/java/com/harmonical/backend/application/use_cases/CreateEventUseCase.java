package com.harmonical.backend.application.use_cases;


import com.harmonical.backend.domain.model.Event;
import com.harmonical.backend.domain.port.EventRepository;
import org.springframework.stereotype.Service;

public class CreateEventUseCase {
    private final EventRepository eventRepository;

    public CreateEventUseCase(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void execute(Event eventData) {
        eventRepository.save(eventData);
    }
}
