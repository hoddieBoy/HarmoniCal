package com.harmonical.backend.infrastructure.persistence.adapter;

import com.harmonical.backend.domain.model.Event;
import com.harmonical.backend.domain.port.EventRepository;
import com.harmonical.backend.infrastructure.persistence.mapper.EventEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({EventRepositoryAdapter.class, EventEntityMapper.class})
class EventRepositoryAdapterTest {

    @Autowired
    private EventRepository eventRepository;

    private Event testEvent;

    @BeforeEach
    void setUp() {
        // Create a test event instance
        testEvent = new Event(UUID.randomUUID(), "Integration Test", "Testing without mocks", LocalDate.now(), LocalTime.NOON, Duration.ofHours(2), "Test Location");
    }

    @Test
    void save_ShouldPersistEventSuccessfully() {
        // Act
        Event savedEvent = (Event) eventRepository.save(testEvent);

        // Assert
        assertNotNull(savedEvent.getId());
        assertEquals(testEvent.getTitle(), savedEvent.getTitle());
        assertEquals(testEvent.getDescription(), savedEvent.getDescription());
        assertEquals(testEvent.getDate(), savedEvent.getDate());
        assertEquals(testEvent.getTime(), savedEvent.getTime());
        assertEquals(testEvent.getDuration(), savedEvent.getDuration());
        assertEquals(testEvent.getLocation(), savedEvent.getLocation());
    }
}
