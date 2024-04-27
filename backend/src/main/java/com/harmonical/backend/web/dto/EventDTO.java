package com.harmonical.backend.web.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventDTO(
        String title,
        String description,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String location
) {
}
