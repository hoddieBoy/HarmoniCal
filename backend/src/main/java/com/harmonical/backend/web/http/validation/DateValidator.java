package com.harmonical.backend.web.http.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator implements ConstraintValidator<ValidDate, String> {

    private String pattern;

    @Override
    public void initialize(ValidDate constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // null is considered valid; use it @NotNull for null checks
        }

        try {
            LocalDate date = LocalDate.parse(value, DateTimeFormatter.ofPattern(pattern));
            return date.isAfter(LocalDate.now()) || date.isEqual(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
