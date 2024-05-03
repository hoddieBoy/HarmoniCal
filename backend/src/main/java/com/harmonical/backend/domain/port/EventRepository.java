package com.harmonical.backend.domain.port;

public interface EventRepository {
    IEvent save(IEvent event);
}
