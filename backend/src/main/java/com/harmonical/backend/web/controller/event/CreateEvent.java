package com.harmonical.backend.web.controller.event;

import com.harmonical.backend.application.use_cases.CreateEventUseCase;
import com.harmonical.backend.web.http.request.CreateEventRequest;
import com.harmonical.backend.web.http.response.EventResponse;
import com.harmonical.backend.web.mapper.EventMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class CreateEvent {

    private final CreateEventUseCase createEventUseCase;
    private final EventMapper eventMapper;

    public CreateEvent(CreateEventUseCase createEventUseCase, EventMapper eventMapper) {
        this.createEventUseCase = createEventUseCase;
        this.eventMapper = eventMapper;
    }

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@RequestBody @Valid CreateEventRequest createEventRequest) {
        return new ResponseEntity<>(
                eventMapper.toResponse(
                        createEventUseCase.execute(
                                eventMapper.toDomain(createEventRequest)
                        )
                ),
                HttpStatus.CREATED
        );
    }
}
