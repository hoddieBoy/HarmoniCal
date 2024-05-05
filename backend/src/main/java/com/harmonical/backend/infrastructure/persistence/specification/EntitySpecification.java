package com.harmonical.backend.infrastructure.persistence.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public record EntitySpecification<T>(SearchCriteria criteria) implements Specification<T> {

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (Objects.isNull(criteria.value())) {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
        if (criteria.operation().equalsIgnoreCase(">")) {
            return criteriaBuilder.greaterThanOrEqualTo(root.get(criteria.key()), criteria.value().toString());
        } else if (criteria.operation().equalsIgnoreCase("<")) {
            return criteriaBuilder.lessThanOrEqualTo(root.get(criteria.key()), criteria.value().toString());
        } else if (criteria.operation().equalsIgnoreCase(":")) {
            if (root.get(criteria.key()).getJavaType() == String.class) {
                return criteriaBuilder.like(
                        root.get(criteria.key()), "%" + criteria.value() + "%"
                );
            } else {
                return criteriaBuilder.equal(root.get(criteria.key()), criteria.value());
            }
        }
        return null;
    }
}
