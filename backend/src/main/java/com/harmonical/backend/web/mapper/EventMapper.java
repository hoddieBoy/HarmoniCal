package com.harmonical.backend.web.mapper;

import com.harmonical.backend.domain.model.Event;
import com.harmonical.backend.domain.port.IEvent;
import com.harmonical.backend.web.http.request.CreateEventRequest;
import com.harmonical.backend.web.http.response.EventResponse;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class EventMapper {

    private EventMapper() {
        // Utility class
    }

    public IEvent toDomain(CreateEventRequest createEventRequest) {
        String duration = createEventRequest.eventDuration();
        Duration eventDuration = null;
        if (duration != null) {
            duration = duration.trim();
            if (!duration.isEmpty()) {
                eventDuration = Duration.parse("PT" + duration.toUpperCase());
            }
        }

        return new Event(
                createEventRequest.eventTitle(),
                createEventRequest.eventDescription(),
                LocalDate.parse(createEventRequest.eventStartDate()),
                createEventRequest.eventStartTime(),
                eventDuration,
                createEventRequest.eventLocation()
        );
    }

    public EventResponse toResponse(IEvent event) {
        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getLocation(),
                event.getDate(),
                event.getTime().truncatedTo(ChronoUnit.MINUTES), // Truncate to minutes to avoid seconds
                String.format("%dh%02dm", event.getDuration().toHours(), event.getDuration().toMinutesPart()),
                event.isPast()
        );
    }
}
