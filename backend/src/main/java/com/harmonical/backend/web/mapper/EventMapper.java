package com.harmonical.backend.web.mapper;

import com.harmonical.backend.web.dto.EventDTO;
import com.harmonical.backend.infrastructure.persistence.model.EventEntity;
import com.harmonical.backend.web.request.CreateEventRequest;

public class EventMapper {

    private EventMapper() {
        // Utility class
    }

    public static EventDTO to(CreateEventRequest request) {
        return new EventDTO(
                request.title(),
                request.description(),
                request.startTime(),
                request.endTime(),
                request.location()
        );
    }

}
