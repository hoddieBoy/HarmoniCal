package com.harmonical.backend.domain.model;

import com.harmonical.backend.domain.port.IEvent;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

public class Event implements IEvent {
    private final UUID id;
    private String title;
    private String description;
    private LocalDate date;

    private LocalTime time;
    private Duration duration;
    private String location;

    public Event(UUID id, String title, String description, LocalDate date, LocalTime time, Duration duration, String location) {
        updateDetails(title, description, location);
        reschedule(date, time, duration);
        this.id = id;
    }

    public Event(String title, String description, LocalDate date, LocalTime time, Duration duration, String location) {
        this(null, title, description, date, time, duration, location);
    }

    @Override
    public UUID getId() {
        return id;
    }
  
    @Override
    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        Objects.requireNonNull(title, "Title cannot be null");

        if (title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be blank");
        }

        this.title = title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public LocalTime getTime() {
        return time;
    }

    @Override
    public Duration getDuration() {
        return this.duration;
    }

    @Override
    public boolean isPast() {
        return LocalDateTime.of(date, time).plus(duration).isBefore(LocalDateTime.now());
    }

    @Override
    public void reschedule(LocalDate date, LocalTime time, Duration duration) {
        Objects.requireNonNull(date, "Date cannot be null");
        // If time and duration are null, then the event is an all-day event
        if (time == null && duration == null) {
            this.date = date;
            this.time = LocalTime.MIN;
            this.duration = Duration.ofDays(1);
        } else {
            Objects.requireNonNull(time, "Time cannot be null");
            Objects.requireNonNull(duration, "Duration cannot be null");

            // check that the Duration is not negative
            if (duration.isNegative()) {
                throw new IllegalArgumentException("Duration cannot be negative");
            }

            this.date = date;
            this.time = time;
            this.duration = duration;
        }

    }

    @Override
    public void updateDetails(String title, String description, String location) {
        setTitle(title);
        this.description = Objects.requireNonNullElse(description, "No description provided");
        this.location = Objects.requireNonNullElse(location, "No location provided");
    }
}
