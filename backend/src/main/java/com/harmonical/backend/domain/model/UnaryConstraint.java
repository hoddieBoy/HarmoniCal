package com.harmonical.backend.domain.model;

import com.harmonical.backend.domain.exception.NotScheduledEventException;
import com.harmonical.backend.domain.port.Constraint;
import com.harmonical.backend.domain.port.IEvent;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class UnaryConstraint implements Constraint {

    private final IEvent event;

    protected UnaryConstraint(IEvent event) {
        Objects.requireNonNull(event, "Event cannot be null");
        this.event = event;
    }

    public IEvent getEvent() {
        return event;
    }

    @Override
    public Set<IEvent> getEvents() {
        return Set.of(event);
    }

    @Override
    public boolean isSatisfiedBy(Map<IEvent, LocalDateTime> schedule) {
        if (!schedule.containsKey(event)) {
            throw new NotScheduledEventException(event);
        }

        return this.check(schedule.get(event));
    }

    protected abstract boolean check(LocalDateTime time);
}
