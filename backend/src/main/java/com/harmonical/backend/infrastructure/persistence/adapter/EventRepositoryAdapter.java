package com.harmonical.backend.infrastructure.persistence.adapter;

import com.harmonical.backend.domain.port.EventRepository;
import com.harmonical.backend.domain.port.IEvent;
import com.harmonical.backend.infrastructure.persistence.mapper.EventEntityMapper;
import com.harmonical.backend.infrastructure.persistence.model.EventEntity;
import com.harmonical.backend.infrastructure.persistence.repository.JPAEventRepository;
import com.harmonical.backend.infrastructure.persistence.specification.EntitySpecification;
import com.harmonical.backend.infrastructure.persistence.specification.SearchCriteria;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
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
    public List<IEvent> findAll(String title, String beginDate, String endDate) {
        Specification<EventEntity> specification = Specification.where(null);
        if (Objects.nonNull(title)) {
            specification = specification.and(new EntitySpecification<>(new SearchCriteria("title", ":", title)));
        }

        if (Objects.nonNull(beginDate)) {
            specification = specification.and(new EntitySpecification<>(new SearchCriteria("beginDate", ">", beginDate)));
        }

        if (Objects.nonNull(endDate)) {
            specification = specification.and(new EntitySpecification<>(new SearchCriteria("endDate", "<", endDate)));
        }

        return jpaEventRepository.findAll(specification)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
