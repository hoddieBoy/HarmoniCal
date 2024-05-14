package com.harmonical.backend.application.use_cases;

import com.harmonical.backend.domain.exception.InvalidIDFormatException;
import com.harmonical.backend.domain.exception.ResourceNotFoundException;
import com.harmonical.backend.domain.port.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteEventUseCase {

    private final EventRepository eventRepository;

    public DeleteEventUseCase(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void execute(String eventId) {
        try {
            UUID uuid = UUID.fromString(eventId);

            eventRepository.findById(uuid)
                    .ifPresentOrElse(
                            eventRepository::delete,
                            () -> {
                                throw new ResourceNotFoundException("Event not found with id: " + eventId);
                            }
                    );
        } catch (IllegalArgumentException e) {
            throw new InvalidIDFormatException("Invalid ID format: " + eventId);
        }
    }
}
