package com.harmonical.infrastructure.persistence.repository;

import com.harmonical.infrastructure.persistence.model.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JPAEventRepository extends JpaRepository<EventEntity, UUID> {
}
