package com.harmonical.backend.domain.model;

import com.harmonical.backend.domain.port.Constraint;
import com.harmonical.backend.domain.port.IEvent;

import java.util.Set;

public abstract class UnaryConstraint implements Constraint {

    private final IEvent event;

    protected UnaryConstraint(IEvent event) {
        this.event = event;
    }

    public IEvent getEvent() {
        return event;
    }

    @Override
    public Set<IEvent> getEvents() {
        return Set.of(event);
    }
}
