package com.harmonical.backend.infrastructure.persistence.adapter;

import com.harmonical.backend.domain.port.EventRepository;
import com.harmonical.backend.domain.port.IEvent;
import com.harmonical.backend.infrastructure.persistence.mapper.EventEntityMapper;
import com.harmonical.backend.infrastructure.persistence.repository.JPAEventRepository;
import com.harmonical.backend.infrastructure.persistence.specification.EventSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Override
    public Optional<IEvent> findById(UUID eventId) {
        return jpaEventRepository.findById(eventId)
                .map(mapper::toDomain);
    }

    @Override
    public List<IEvent> findAll(String title, LocalDate beginDate, LocalDate endDate) {
        return jpaEventRepository.findAll(
                        Specification.where(
                                new EventSpecification(
                                        title,
                                        beginDate,
                                        endDate
                                )
                        )
                ).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(IEvent event) {
        jpaEventRepository.deleteById(event.getId());
    }
}
