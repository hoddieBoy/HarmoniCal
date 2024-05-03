package com.harmonical.backend.infrastructure.persistence.mapper;

import com.harmonical.backend.domain.model.Event;
import com.harmonical.backend.domain.port.IEvent;
import com.harmonical.backend.infrastructure.persistence.model.EventEntity;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class EventEntityMapper {

    private EventEntityMapper() {
        // Utility class
    }

    public EventEntity toEntity(IEvent event) {
        return new EventEntity()
                .setId(event.getId() == null ? UUID.randomUUID() : event.getId())
                .setTitle(event.getTitle())
                .setDescription(event.getDescription())
                .setDate(event.getDate())
                .setTime(event.getTime())
                .setDuration(event.getDuration())
                .setLocation(event.getLocation());
    }

    public IEvent toDomain(EventEntity eventEntity) {
        return new Event(
                eventEntity.getId(),
                eventEntity.getTitle(),
                eventEntity.getDescription(),
                eventEntity.getDate(),
                eventEntity.getTime(),
                eventEntity.getDuration(),
                eventEntity.getLocation()
        );
    }
}
