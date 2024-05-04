package com.harmonical.backend.application.use_cases;

import com.harmonical.backend.domain.exception.ResourceNotFoundException;
import com.harmonical.backend.domain.port.EventRepository;
import com.harmonical.backend.domain.port.IEvent;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReadEventUseCase {
    private final EventRepository eventRepository;

    public ReadEventUseCase(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public IEvent execute(UUID eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));
    }
}
