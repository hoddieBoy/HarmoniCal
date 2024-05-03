package com.harmonical.backend.infrastructure.persistence.repository;

import com.harmonical.backend.infrastructure.persistence.model.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JPAEventRepository extends JpaRepository<EventEntity, UUID> {
}
