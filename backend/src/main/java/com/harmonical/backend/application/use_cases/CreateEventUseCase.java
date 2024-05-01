package com.harmonical.backend.application.use_cases;

import com.harmonical.backend.domain.port.EventRepository;
import com.harmonical.backend.domain.port.IEvent;
import jakarta.validation.ConstraintViolationException;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CreateEventUseCase {

    private final EventRepository eventRepository;

    public CreateEventUseCase(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public IEvent execute(IEvent event) {
        Objects.requireNonNull(event, "Event must not be null");
        validate(event);

        IEvent savedEvent = eventRepository.save(event);

        // Check that id is not null
        Objects.requireNonNull(savedEvent.getId(), "Saved event ID must not be null");

        return savedEvent;
    }

    public void validate(IEvent event) throws ConstraintViolationException {
        if (event.getId() != null) {
            throw new ConstraintViolationException("Event ID must be null", null);
        }

        if (event.isPast()) {
            throw new ConstraintViolationException("Event cannot be in the past", null);
        }
    }
}
