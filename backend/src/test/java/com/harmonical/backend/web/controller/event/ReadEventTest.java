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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Given: A request to get an event")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class ReadEventTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private EventRepository eventRepository;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/events";
    }

    @DisplayName("When: A valid event ID is provided")
    @Nested
    class ValidEventId {

        @Test
        @DisplayName("Then: The event is returned")
        void testReadEvent() {
            IEvent savedEvent = eventRepository.save(new Event(
                    "Test Event",
                    "Test Description",
                    LocalDate.now().plusDays(1),
                    LocalTime.NOON,
                    Duration.ofHours(2),
                    "Test Location"
            ));

            webTestClient.get()
                    .uri(getBaseUrl() + "/" + savedEvent.getId())
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.id").isEqualTo(savedEvent.getId().toString())
                    .jsonPath("$.details.title").isEqualTo(savedEvent.getTitle())
                    .jsonPath("$.details.description").isEqualTo(savedEvent.getDescription())
                    .jsonPath("$.details.location").isEqualTo(savedEvent.getLocation())
                    .jsonPath("$.schedule.date").isEqualTo(savedEvent.getDate().toString())
                    .jsonPath("$.schedule.time").isEqualTo("12:00:00")
                    .jsonPath("$.schedule.duration").isEqualTo("2h00m")
                    .jsonPath("$.status.is_past_event").isEqualTo(savedEvent.isPast());
        }
    }

    @DisplayName("When: An invalid event ID is provided")
    @Nested
    class InvalidEventId {

        @Test
        @DisplayName("Then: A 404 status is returned")
        void testReadEvent() {
            UUID invalidId = UUID.randomUUID();
            webTestClient.get()
                    .uri(getBaseUrl() + "/" + invalidId)
                    .exchange()
                    .expectStatus().isNotFound()
                    .expectBody()
                    .jsonPath("$.message").isEqualTo("Event not found with id: " + invalidId);
        }
    }

}
