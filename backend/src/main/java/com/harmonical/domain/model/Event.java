package com.harmonical.domain.model;


import java.time.LocalDateTime;
import java.util.UUID;

public class Event {
    private final UUID id;

    private String title;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String location;

    public Event(UUID id, String title, String description, LocalDateTime startTime, LocalDateTime endTime, String location) {
        this.id = id;
        setTitle(title);
        setDescription(description);
        setTimesPeriod(startTime, endTime);
        setLocation(location);
    }

    public Event reschedule(LocalDateTime startTime, LocalDateTime endTime) {
        setTimesPeriod(startTime, endTime);
        return this;
    }

    public Event update(String title, String description, String location) {
        setTitle(title);
        setDescription(description);
        setLocation(location);
        return this;
    }

    private void setTimesPeriod(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Start time and end time cannot be null");
        }

        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time cannot be after end time");
        }

        this.startTime = startTime;
        this.endTime = endTime;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }

        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getLocation() {
        return location;
    }

    private void setLocation(String location) {
        this.location = location;
    }
}
