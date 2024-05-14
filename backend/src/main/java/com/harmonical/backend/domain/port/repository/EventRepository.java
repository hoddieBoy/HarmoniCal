package com.harmonical.backend.domain.port.repository;

import com.harmonical.backend.domain.port.IEvent;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository {
    IEvent save(IEvent event);

    Optional<IEvent> findById(UUID eventId);

    List<IEvent> findAll(String search, LocalDate beginDate, LocalDate endDate);

    void delete(IEvent event);
}
