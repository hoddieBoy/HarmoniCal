package com.harmonical.backend.application.use_cases;

import com.harmonical.backend.domain.exception.InvalidDateFormatException;
import com.harmonical.backend.domain.port.EventRepository;
import com.harmonical.backend.domain.port.IEvent;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class ReadListEventUseCase {

    private final EventRepository eventRepository;

    public ReadListEventUseCase(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<IEvent> execute(String search, String beginDate, String endDate) {
        LocalDate begin = null;
        LocalDate end = null;

        if (beginDate != null && !beginDate.isEmpty()) {
            try {
                begin = LocalDate.parse(beginDate);
            } catch (DateTimeParseException e) {
                throw new InvalidDateFormatException("Invalid format for begin date. Expected format is YYYY-MM-DD.");
            }
        }

        if (endDate != null && !endDate.isEmpty()) {
            if (begin == null) {
                throw new InvalidDateFormatException("End date cannot be provided without begin date.");
            }

            try {
                end = LocalDate.parse(endDate);
            } catch (DateTimeParseException e) {
                throw new InvalidDateFormatException("Invalid format for end date. Expected format is YYYY-MM-DD.");
            }
        }

        return eventRepository.findAll(search, begin, end);
    }
}
