package com.harmonical.backend.infrastructure.persistence.mapper;

import com.harmonical.backend.domain.model.Event;
import com.harmonical.backend.domain.port.IEvent;
import com.harmonical.backend.infrastructure.persistence.model.EventEntity;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.harmonical.backend.shared.EventConstants.*;

@Component
public class EventEntityMapper {

    private EventEntityMapper() {
        // Utility class
    }

    public EventEntity toEntity(IEvent event) {
        LocalDateTime startTime = LocalDateTime.of(event.getDate(), event.getTime());
        return new EventEntity()
                .setId(event.getId() == null ? UUID.randomUUID() : event.getId())
                .setTitle(event.getTitle())
                .setDescription(event.getDescription())
                .setStartTime(startTime)
                .setEndTime(startTime.plus(event.getDuration()))
                .setLocation(event.getLocation());
    }

    public IEvent toDomain(EventEntity eventEntity) {
        Duration duration = Duration.between(eventEntity.getStartTime(), eventEntity.getEndTime());
        return new Event(
                eventEntity.getId(),
                eventEntity.getTitle(),
                eventEntity.getDescription(),
                eventEntity.getStartTime().toLocalDate(),
                eventEntity.getStartTime().toLocalTime(),
                duration,
                eventEntity.getLocation()
        );
    }
}
