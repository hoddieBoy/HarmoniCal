package com.harmonical.backend.web.http.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = TimeAndDurationConsistencyValidator.class)
public @interface TimeAndDurationConsistency {
    String message() default "Start time and duration must be both present or both absent";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
