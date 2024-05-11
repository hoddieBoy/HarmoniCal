package com.harmonical.backend.infrastructure.persistence.specification;

import com.harmonical.backend.infrastructure.persistence.model.EventEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record EventSpecification(
        String search,
        LocalDate beginDate,
        LocalDate endDate
) implements Specification<EventEntity> {
    @Override
    public Predicate toPredicate(Root<EventEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(search) && !search.isBlank()) {
            predicates.add(getSearchPredicate(root, criteriaBuilder));
        }

        if (Objects.nonNull(beginDate)) {
            predicates.add(getBeginDatePredicate(root, criteriaBuilder));

            if (Objects.nonNull(endDate)) {
                predicates.add(getEndDatePredicate(root, criteriaBuilder));
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate getSearchPredicate(Root<EventEntity> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.or(
                criteriaBuilder.like(root.get("title"), "%" + search + "%"),
                criteriaBuilder.like(root.get("description"), "%" + search + "%")
        );
    }

    private Predicate getBeginDatePredicate(Root<EventEntity> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.greaterThanOrEqualTo(root.get("date"), beginDate);
    }

    private Predicate getEndDatePredicate(Root<EventEntity> root, CriteriaBuilder criteriaBuilder) {
        Period period = Period.between(beginDate, endDate);
        return criteriaBuilder.lessThanOrEqualTo(
                root.get("duration"),
                Duration.ofDays(period.getDays())
        );
    }
}
