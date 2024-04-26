package com.harmonical.application.mapper;

import com.harmonical.domain.model.Event;
import com.harmonical.infrastructure.persistence.model.EventEntity;

public class EventMapper {

    private EventMapper() {
        // Utility class
    }

    public static EventEntity toEntity(Event event) {
        if (event == null) {
            return null;
        }

        return new EventEntity(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getStartTime(),
                event.getEndTime(),
                event.getLocation()
        );
    }

    public static Event toDomain(EventEntity eventEntity) {
        if (eventEntity == null) {
            return null;
        }

        return new Event(
                eventEntity.getId(),
                eventEntity.getTitle(),
                eventEntity.getDescription(),
                eventEntity.getStartTime(),
                eventEntity.getEndTime(),
                eventEntity.getLocation()
        );
    }
}
