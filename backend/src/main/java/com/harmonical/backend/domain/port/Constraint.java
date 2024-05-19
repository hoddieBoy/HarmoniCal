package com.harmonical.backend.domain.port;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

public interface Constraint {

    Set<IEvent> getEvents();

    boolean isSatisfiedBy(Map<IEvent, LocalDateTime> schedule);
}
