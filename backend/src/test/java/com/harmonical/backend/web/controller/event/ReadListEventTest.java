package com.harmonical.backend.web.controller.event;

import com.harmonical.backend.domain.model.Event;
import com.harmonical.backend.domain.port.EventRepository;
import com.harmonical.backend.web.http.response.EventResponse;
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

@DisplayName("Given: A request to get a list of events")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class ReadListEventTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private EventRepository eventRepository;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/events";
    }

    @BeforeEach
    void setUp() {
        // Populate the database with events
        for (int i = 0; i < 3; i++) {
            eventRepository.save(new Event(
                    "Test Event " + i,
                    "Test Description " + i,
                    LocalDate.now().plusDays(i),
                    LocalTime.NOON,
                    Duration.ofHours(2),
                    "Test Location " + i
            ));
        }
    }

    @DisplayName("When: No parameters are provided")
    @Nested
    class NoParameters {

        @Test
        @DisplayName("Then: All events are returned")
        void testReadListEvent() {
            webTestClient.get()
                    .uri(getBaseUrl())
                    .exchange()
                    .expectStatus().isOk()
                    .expectBodyList(EventResponse.class)
                    .hasSize(3);
        }
    }

    @DisplayName("When: a search parameter is provided")
    @Nested
    class Title {

        @Test
        @DisplayName("Then: Only events with the provided title are returned")
        void testReadListEvent() {
            webTestClient.get()
                    .uri(getBaseUrl() + "?search=Test Event 1")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBodyList(EventResponse.class)
                    .hasSize(1);
        }
    }

    @DisplayName("When: A begin date is provided")
    @Nested
    class BeginDate {

        @Test
        @DisplayName("Then: Only events after the provided date are returned")
        void testReadListEvent() {
            webTestClient.get()
                    .uri(getBaseUrl() + "?beginDate=" + LocalDate.now().plusDays(1))
                    .exchange()
                    .expectStatus().isOk()
                    .expectBodyList(EventResponse.class)
                    .hasSize(2);
        }
    }

    @DisplayName("When: An invalid begin date is provided")
    @Nested
    class InvalidBeginDate {

        @Test
        @DisplayName("Then: A 400 status is returned")
        void testReadListEvent() {
            webTestClient.get()
                    .uri(getBaseUrl() + "?beginDate=2022-01-01T00:00:00")
                    .exchange()
                    .expectStatus().isBadRequest()
                    .expectBody()
                    .jsonPath("$.message").isEqualTo("Invalid format for begin date. Expected format is YYYY-MM-DD.");
        }
    }

    @DisplayName("When: An end date is provided without a begin date")
    @Nested
    class EndDateWithoutBeginDate {

        @Test
        @DisplayName("Then: A 400 status is returned")
        void testReadListEvent() {
            webTestClient.get()
                    .uri(getBaseUrl() + "?endDate=" + LocalDate.now().plusDays(1))
                    .exchange()
                    .expectStatus().isBadRequest()
                    .expectBody()
                    .jsonPath("$.message").isEqualTo("End date cannot be provided without begin date.");
        }
    }

    @DisplayName("When: An invalid end date is provided")
    @Nested
    class InvalidEndDate {

        @Test
        @DisplayName("Then: A 400 status is returned")
        void testReadListEvent() {
            webTestClient.get()
                    .uri(getBaseUrl() + "?beginDate=" + LocalDate.now().plusDays(1) + "&endDate=2022-01-01T00:00:00")
                    .exchange()
                    .expectStatus().isBadRequest()
                    .expectBody()
                    .jsonPath("$.message").isEqualTo("Invalid format for end date. Expected format is YYYY-MM-DD.");
        }
    }

    @DisplayName("When: a begin date and an end date are provided")
    @Nested
    class BeginDateAndEndDate {

        @Test
        @DisplayName("Then: Only events between the provided dates are returned")
        void testReadListEvent() {
            webTestClient.get()
                    .uri(getBaseUrl() + "?beginDate=" + LocalDate.now().plusDays(1) + "&endDate=" + LocalDate.now().plusDays(2))
                    .exchange()
                    .expectStatus().isOk()
                    .expectBodyList(EventResponse.class)
                    .hasSize(2);
        }

    }
}