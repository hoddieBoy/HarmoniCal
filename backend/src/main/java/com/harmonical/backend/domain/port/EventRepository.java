package com.harmonical.backend.domain.port;

import java.util.Optional;
import java.util.UUID;

public interface EventRepository {
    IEvent save(IEvent event);

    Optional<IEvent> findById(UUID eventId);
}
