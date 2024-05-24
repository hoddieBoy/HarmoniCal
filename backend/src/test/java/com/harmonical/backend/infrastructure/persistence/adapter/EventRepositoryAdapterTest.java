package com.harmonical.backend.infrastructure.persistence.adapter;

import com.harmonical.backend.domain.model.Event;
import com.harmonical.backend.domain.port.EventRepository;
import com.harmonical.backend.domain.port.IEvent;
import com.harmonical.backend.infrastructure.persistence.mapper.EventEntityMapper;
import com.harmonical.backend.infrastructure.persistence.model.EventEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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

    @Autowired
    private TestEntityManager entityManager;

    private Event testEvent;

    @BeforeEach
    void setUp() {
        testEvent = createTestEvent();
    }

    private Event createTestEvent() {
        return new Event(
                UUID.randomUUID(),
                "Integration Test",
                "Testing without mocks",
                LocalDate.now(),
                LocalTime.NOON,
                Duration.ofHours(2),
                "Test Location"
        );
    }

    @Test
    void save_ShouldPersistEventSuccessfully() {
        IEvent savedEvent = eventRepository.save(testEvent);
        assertEventFields(testEvent, savedEvent);
        assertPersistence(savedEvent);
    }

    private void assertEventFields(Event expected, IEvent actual) {
        assertNotNull(actual, "Saved event should not be null");
        assertNotNull(actual.getId(), "Saved event ID should not be null");
        assertEquals(expected.getTitle(), actual.getTitle(), "Event title should match");
        assertEquals(expected.getDescription(), actual.getDescription(), "Event description should match");
        assertEquals(expected.getDate(), actual.getDate(), "Event date should match");
        assertEquals(expected.getTime(), actual.getTime(), "Event time should match");
        assertEquals(expected.getDuration(), actual.getDuration(), "Event duration should match");
        assertEquals(expected.getLocation(), actual.getLocation(), "Event location should match");
    }

    private void assertPersistence(IEvent savedEvent) {
        EventEntity eventEntity = entityManager.find(EventEntity.class, savedEvent.getId());
        assertNotNull(eventEntity, "Event entity should not be null");
        assertEquals(savedEvent.getId(), eventEntity.getId(), "Entity ID should match saved event ID");
        assertEquals(savedEvent.getTitle(), eventEntity.getTitle(), "Entity title should match");
        assertEquals(savedEvent.getDescription(), eventEntity.getDescription(), "Entity description should match");
        assertEquals(savedEvent.getDate(), eventEntity.getDate(), "Entity date should match");
        assertEquals(savedEvent.getTime(), eventEntity.getTime(), "Entity time should match");
        assertEquals(savedEvent.getDuration(), eventEntity.getDuration(), "Entity duration should match");
        assertEquals(savedEvent.getLocation(), eventEntity.getLocation(), "Entity location should match");
        assertNotNull(eventEntity.getCreatedAt(), "Entity created at should not be null");
        assertNotNull(eventEntity.getUpdatedAt(), "Entity updated at should not be null");
        assertTrue(eventEntity.getCreatedAt().isEqual(eventEntity.getUpdatedAt()), "Entity created at should match updated at");
    }
}
