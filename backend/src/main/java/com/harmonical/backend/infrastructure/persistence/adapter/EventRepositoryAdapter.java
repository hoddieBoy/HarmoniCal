package com.harmonical.backend.infrastructure.persistence.adapter;

import com.harmonical.backend.domain.port.EventRepository;
import com.harmonical.backend.domain.port.IEvent;
import com.harmonical.backend.infrastructure.persistence.mapper.EventEntityMapper;
import com.harmonical.backend.infrastructure.persistence.repository.JPAEventRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class EventRepositoryAdapter implements EventRepository {
    private final JPAEventRepository jpaEventRepository;
    private final EventEntityMapper mapper;

    public EventRepositoryAdapter(JPAEventRepository jpaEventRepository, EventEntityMapper eventEntityMapper) {
        this.jpaEventRepository = jpaEventRepository;
        this.mapper = eventEntityMapper;
    }

    @Override
    @Transactional
    public IEvent save(IEvent event) {
        return mapper.toDomain(
                jpaEventRepository.save(
                        mapper.toEntity(event)
                )
        );
    }
}
