package com.harmonical.backend.infrastructure.persistence.listener;

import com.harmonical.backend.infrastructure.persistence.model.EventEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class EventEntityListener {

    @PrePersist
    public void onPrePersist(EventEntity eventEntity) {
        LocalDateTime now = LocalDateTime.now();
        eventEntity.setCreatedAt(now);
        eventEntity.setUpdatedAt(now);
    }

    @PreUpdate
    public void onPreUpdate(EventEntity eventEntity) {
        eventEntity.setUpdatedAt(LocalDateTime.now());
    }
}
