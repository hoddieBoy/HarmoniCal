package com.harmonical.backend.web.http.validation;

import com.harmonical.backend.web.http.request.CreateEventRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TimeAndDurationConsistencyValidator implements ConstraintValidator<TimeAndDurationConsistency, CreateEventRequest> {

    // start time and duration are optional, but if one is present, the other must be present as well
    @Override
    public boolean isValid(CreateEventRequest value, ConstraintValidatorContext context) {
        return (value.eventStartTime() == null && value.eventDuration() == null) ||
                (value.eventStartTime() != null && value.eventDuration() != null);
    }
}
