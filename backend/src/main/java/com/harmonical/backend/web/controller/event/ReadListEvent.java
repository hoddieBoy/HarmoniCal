package com.harmonical.backend.web.controller.event;

import com.harmonical.backend.application.use_cases.ReadListEventUseCase;
import com.harmonical.backend.web.http.response.EventResponse;
import com.harmonical.backend.web.mapper.EventMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class ReadListEvent {

    private final ReadListEventUseCase readListEventUseCase;

    private final EventMapper eventMapper;

    public ReadListEvent(ReadListEventUseCase readListEventUseCase, EventMapper eventMapper) {
        this.readListEventUseCase = readListEventUseCase;
        this.eventMapper = eventMapper;
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> readListEvent(@RequestParam(required = false) String search,
                                                             @RequestParam(required = false) String beginDate,
                                                             @RequestParam(required = false) String endDate) {
        return new ResponseEntity<>(
                readListEventUseCase.execute(search, beginDate, endDate)
                        .stream()
                        .map(eventMapper::toResponse)
                        .toList(),
                HttpStatus.OK
        );
    }
}
