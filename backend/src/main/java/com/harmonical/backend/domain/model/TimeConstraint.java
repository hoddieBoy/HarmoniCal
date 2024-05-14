package com.harmonical.backend.domain.model;

import com.harmonical.backend.domain.port.IEvent;

import java.time.LocalTime;
import java.util.Map;

public class TimeConstraint extends UnaryConstraint {

    private final LocalTime beginTime;

    private final LocalTime endTime;

    public TimeConstraint(IEvent event, LocalTime beginTime, LocalTime endTime) {
        super(event);
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public LocalTime getBeginTime() {
        return beginTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public boolean isSatisfiedBy(Map<IEvent, LocalTime> schedule) {
        return schedule.containsKey(getEvent()) && schedule.get(getEvent()).isAfter(beginTime) && schedule.get(getEvent()).isBefore(endTime);
    }
}
