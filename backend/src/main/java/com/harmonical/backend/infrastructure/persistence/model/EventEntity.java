package com.harmonical.backend.infrastructure.persistence.model;

import com.harmonical.backend.infrastructure.persistence.listener.EventEntityListener;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Table(name = "events")
@Entity
@EntityListeners({EventEntityListener.class})
public class EventEntity {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "time", nullable = false)
    private LocalTime time;

    @Column(name = "duration", nullable = false)
    private Duration duration;

    @Column(name = "location", nullable = false)
    private String location;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public EventEntity() {
        // JPA requires a default constructor for entity classes because it needs to create instances of the entity class using reflection.
    }

    public UUID getId() {
        return id;
    }

    public EventEntity setId(UUID id) {
        this.id = id;

        return this;
    }

    public String getTitle() {
        return title;
    }

    public EventEntity setTitle(String title) {
        this.title = title;

        return this;
    }

    public String getDescription() {
        return description;
    }

    public EventEntity setDescription(String description) {
        this.description = description;

        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public EventEntity setDate(LocalDate date) {
        this.date = date;

        return this;
    }

    public LocalTime getTime() {
        return time;
    }

    public EventEntity setTime(LocalTime time) {
        this.time = time;

        return this;
    }

    public String getLocation() {
        return location;
    }

    public EventEntity setLocation(String location) {
        this.location = location;

        return this;
    }

    public Duration getDuration() {
        return duration;
    }

    public EventEntity setDuration(Duration duration) {
        this.duration = duration;

        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public EventEntity setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;

        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public EventEntity setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;

        return this;
    }
}
