package com.harmonical.backend.web.http.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record EventResponse(
        UUID id,
        Details details,
        Schedule schedule,
        Status status
) {
    public EventResponse(UUID id, String title, String description, String location, LocalDate date, LocalTime time, String duration, boolean past) {
        this(id, new Details(title, description, location), new Schedule(date, time, duration), new Status(past));
    }

    private record Details(
            String title,
            String description,
            String location
    ) {
    }

    private record Schedule(
            LocalDate date,
            LocalTime time,
            String duration
    ) {
    }

    private record Status(
            boolean isPastEvent
    ) {
    }
}

