package com.harmonical.backend.domain.model;

import com.harmonical.backend.domain.port.IEvent;

import java.time.LocalDateTime;

public class TimeConstraint extends UnaryConstraint {

    private final LocalDateTime beginTime;

    private final LocalDateTime endTime;

    public TimeConstraint(IEvent event, LocalDateTime beginTime, LocalDateTime endTime) {
        super(event);

        if (beginTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Begin time cannot be after end time");
        }

        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public boolean check(LocalDateTime time) {
        return (time.isEqual(beginTime) || time.isAfter(beginTime)) && (time.isEqual(endTime) || time.isBefore(endTime));
    }
}
