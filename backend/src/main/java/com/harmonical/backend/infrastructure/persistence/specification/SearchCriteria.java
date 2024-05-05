package com.harmonical.backend.infrastructure.persistence.specification;

public record SearchCriteria(
        String key,
        String operation,
        Object value
) {
}
