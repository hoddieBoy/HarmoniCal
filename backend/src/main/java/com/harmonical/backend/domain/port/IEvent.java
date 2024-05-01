package com.harmonical.backend.domain.port;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.UUID;

public interface IEvent {

    UUID getId();

    String getTitle();

    String getDescription();

    String getLocation();

    LocalDate getDate();

    LocalTime getTime();

    Duration getDuration();

    boolean isPast();

    void reschedule(LocalDate date, LocalTime time, Duration duration);

    void updateDetails(String title, String description, String location);
}
