package com.harmonical.backend.domain.port;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository {
    IEvent save(IEvent event);

    Optional<IEvent> findById(UUID eventId);

    List<IEvent> findAll(String name, String beginDate, String endDate);
}
