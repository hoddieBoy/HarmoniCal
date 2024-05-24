package com.harmonical.backend.web.controller.event;

import com.harmonical.backend.application.use_cases.ReadEventUseCase;
import com.harmonical.backend.web.http.response.EventResponse;
import com.harmonical.backend.web.mapper.EventMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class ReadEvent {

    private final ReadEventUseCase readEventUseCase;

    private final EventMapper eventMapper;

    public ReadEvent(ReadEventUseCase readEventUseCase, EventMapper eventMapper) {
        this.readEventUseCase = readEventUseCase;
        this.eventMapper = eventMapper;
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> readEvent(@PathVariable UUID eventId) {
        return new ResponseEntity<>(
                eventMapper.toResponse(
                        readEventUseCase.execute(eventId)
                ),
                HttpStatus.OK
        );
    }
}
