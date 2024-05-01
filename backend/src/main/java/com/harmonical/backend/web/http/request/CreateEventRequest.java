package com.harmonical.backend.web.http.request;

import com.harmonical.backend.web.http.validation.TimeAndDurationConsistency;
import com.harmonical.backend.web.http.validation.ValidDate;
import jakarta.validation.constraints.*;

import java.time.LocalTime;

@TimeAndDurationConsistency
public record CreateEventRequest
        (
                @NotBlank(message = "Title is required")
                String eventTitle,
                String eventDescription,
                @NotNull(message = "Start date is required")
                @ValidDate(message = "Start date must be in the present or future and follow the pattern yyyy-MM-dd")
                String eventStartDate,
                LocalTime eventStartTime,
                @Pattern(regexp = PATTERN_DURATION, message = "Invalid duration format")
                String eventDuration,
                String eventLocation
        ) {

    // Jour, heure, minute
    private static final String PATTERN_DURATION = "^(?:(\\d{1,2})\\s*[hH])?(?:\\s*(\\d{1,2})\\s*[mM])?$";
}
