package com.harmonical.backend.web.controller;

import com.harmonical.backend.web.dto.EventDTO;
import com.harmonical.backend.application.use_cases.CreateEventUseCase;
import com.harmonical.backend.web.request.CreateEventRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final CreateEventUseCase createEventUseCase;

    public EventController(CreateEventUseCase createEventUseCase) {
        this.createEventUseCase = createEventUseCase;
    }

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@Validated @RequestBody CreateEventRequest request) {
        EventDTO inputEvent = new EventDTO(
                request.title(),
                request.description(),
                request.startTime(),
                request.endTime(),
                request.location()
        );
        EventDTO eventDTO = createEventUseCase.execute(inputEvent);

        return ResponseEntity.status(HttpStatus.CREATED).body(eventDTO);
    }
}
