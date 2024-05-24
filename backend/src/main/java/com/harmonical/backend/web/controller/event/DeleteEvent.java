package com.harmonical.backend.web.controller.event;

import com.harmonical.backend.application.use_cases.DeleteEventUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class DeleteEvent {

    private final DeleteEventUseCase deleteEventUseCase;

    public DeleteEvent(DeleteEventUseCase deleteEventUseCase) {
        this.deleteEventUseCase = deleteEventUseCase;
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String eventId) {
        deleteEventUseCase.execute(eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
