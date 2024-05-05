package com.harmonical.backend.application.use_cases;

import com.harmonical.backend.domain.port.EventRepository;
import com.harmonical.backend.domain.port.IEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadListEventUseCase {

    private final EventRepository eventRepository;

    public ReadListEventUseCase(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<IEvent> execute(String title, String beginDate, String endDate) {
        return eventRepository.findAll(title, beginDate, endDate);
    }
}
