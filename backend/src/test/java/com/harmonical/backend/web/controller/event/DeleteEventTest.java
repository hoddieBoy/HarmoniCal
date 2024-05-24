package com.harmonical.backend.web.controller.event;

import com.harmonical.backend.domain.model.Event;
import com.harmonical.backend.domain.port.EventRepository;
import com.harmonical.backend.domain.port.IEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("Given: A request to delete an event")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class DeleteEventTest {

    @Autowired
    private WebTestClient webTestClient;

    @LocalServerPort
    private int port;

    @Autowired
    private EventRepository eventRepository;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/events";
    }

    @DisplayName("When: A valid event ID is provided")
    @Nested
    class ValidEventId {

        private IEvent savedEvent;

        @BeforeEach
        void setUp() {
            this.savedEvent = eventRepository.save(new Event(
                    "Test Event",
                    "Test Description",
                    LocalDate.now().plusDays(1),
                    LocalTime.NOON,
                    Duration.ofHours(2),
                    "Test Location"
            ));
        }

        @Test
        @DisplayName("Then: The event is deleted")
        void testDeleteEvent() {
            webTestClient.delete()
                    .uri(getBaseUrl() + "/" + savedEvent.getId())
                    .exchange()
                    .expectStatus().isNoContent();

            assertFalse(eventRepository.findById(savedEvent.getId()).isPresent());
        }

    }

    @DisplayName("When: An invalid event ID is provided")
    @Nested
    class InvalidEventId {
        @DisplayName("Then: A 404 status is returned when the format is invalid")
        @Test
        void testDeleteEventWithInvalidId() {
            // Invalid ID format
            webTestClient.delete()
                    .uri(getBaseUrl() + "/invalid-id")
                    .exchange()
                    .expectStatus().isBadRequest()
                    .expectBody()
                    .jsonPath("$.message").isEqualTo("Invalid ID format: invalid-id");
        }

        @DisplayName("Then: A 404 status is returned when the event does not exist")
        @Test
        void testDeleteEventWithNonExistentId() {
            // Event does not exist
            webTestClient.delete()
                    .uri(getBaseUrl() + "/00000000-0000-0000-0000-000000000000")
                    .exchange()
                    .expectStatus().isNotFound()
                    .expectBody()
                    .jsonPath("$.message").isEqualTo("Event not found with id: 00000000-0000-0000-0000-000000000000");
        }
    }
}