package com.harmonical.backend.web.controller.event;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Given: A request to create an event")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class CreateEventTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    private LocalDateTime now;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/events";
    }

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
    }

    @DisplayName("When: The request includes all required fields and optional fields")
    @Nested
    class WhenRequestIncludesAllFields {

        @Test
        @DisplayName("Then: The event is created successfully and reflecting all fields")
        void createEvent_Successfully() {
            // Arrange
            LocalDate startDate = LocalDate.now().plusDays(1);
            String startTime = "12:00:00";
            String title = "Board Meeting";
            String description = "Monthly strategic meeting";
            String duration = "2h30m";
            String location = "Conference Room A";
            String requestBody = """
                    {
                        "event_title": "%s",
                        "event_description": "%s",
                        "event_location": "%s",
                        "event_start_date": "%s",
                        "event_start_time": "%s",
                        "event_duration": "%s"
                    }
                    """.formatted(title, description, location, startDate, startTime, duration);

            // Act
            webTestClient.post()
                    .uri(getBaseUrl())
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .body(Mono.just(requestBody), String.class)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectBody()
                    .jsonPath("$.id").isNotEmpty()
                    .jsonPath("$.details.title").isEqualTo(title)
                    .jsonPath("$.details.description").isEqualTo(description)
                    .jsonPath("$.details.location").isEqualTo(location)
                    .jsonPath("$.schedule.date").isEqualTo(startDate.toString())
                    .jsonPath("$.schedule.time").isEqualTo(startTime)
                    .jsonPath("$.schedule.duration").isEqualTo(duration)
                    .jsonPath("$.status.is_past_event").isEqualTo(false);
        }
    }

    @DisplayName("When: The request includes all required fields only")
    @Nested
    class WhenRequestIncludesOnlyRequiredFields {

        @Test
        @DisplayName("Then: The event is created successfully and reflecting all fields required fields and default values for optional fields")
        void createEvent_Successfully() {
            // Arrange
            LocalDate startDate = LocalDate.now().plusDays(1);
            String title = "Board Meeting";
            String description = "Monthly strategic meeting";
            String requestBody = """
                    {
                        "event_title": "%s",
                        "event_start_date": "%s"
                    }
                    """.formatted(title, startDate);

            // Act
            webTestClient.post()
                    .uri(getBaseUrl())
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .body(Mono.just(requestBody), String.class)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectBody()
                    .jsonPath("$.id").isNotEmpty()
                    .jsonPath("$.details.title").isEqualTo(title)
                    .jsonPath("$.details.description").isEqualTo("No description provided")
                    .jsonPath("$.details.location").isEqualTo("No location provided")
                    .jsonPath("$.schedule.date").isEqualTo(startDate.toString())
                    .jsonPath("$.schedule.time").isEqualTo("00:00:00")
                    .jsonPath("$.status.is_past_event").isEqualTo(false);
        }
    }

    @DisplayName("When: The title is missing")
    @Nested
    class WhenTitleIsMissing {

        @Test
        @DisplayName("Then: The request fails with a 400 Bad Request status code")
        void createEvent_Fails() {
            // Arrange
            LocalDate startDate = LocalDate.now().plusDays(1);
            String requestBody = """
                    {
                        "event_start_date": "%s"
                    }
                    """.formatted(startDate);

            // Act
            webTestClient.post()
                    .uri(getBaseUrl())
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .body(Mono.just(requestBody), String.class)
                    .exchange()
                    .expectStatus().isBadRequest();
        }
    }

    @DisplayName("When: The title is blank or empty")
    @Nested
    class WhenTitleIsBlankOrEmpty {

        @ParameterizedTest
        @DisplayName("Then: The request fails with a 400 Bad Request status code")
        @ValueSource(strings = { "  ", "\\t", "\\n" })
        void createEvent_Fails(String invalidTitle) {
            // Arrange
            LocalDate startDate = LocalDate.now().plusDays(1);
            String requestBody = """
                    {
                        "event_title": "%s",
                        "event_start_date": "%s"
                    }
                    """.formatted(invalidTitle, startDate);

            // Act
            webTestClient.post()
                    .uri(getBaseUrl())
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .body(Mono.just(requestBody), String.class)
                    .exchange()
                    .expectStatus().isBadRequest();
        }
    }

    @DisplayName("When: The start date is missing")
    @Nested
    class WhenStartDateIsMissing {

        @Test
        @DisplayName("Then: The request fails with a 400 Bad Request status code")
        void createEvent_Fails() {
            // Arrange
            String title = "Board Meeting";
            String requestBody = """
                    {
                        "event_title": "%s"
                    }
                    """.formatted(title);

            // Act
            webTestClient.post()
                    .uri(getBaseUrl())
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .body(Mono.just(requestBody), String.class)
                    .exchange()
                    .expectStatus().isBadRequest();
        }
    }

    @DisplayName("When: The start date is in the past")
    @Nested
    class WhenStartDateIsInThePast {

        @Test
        @DisplayName("Then: The request fails with a 400 Bad Request status code")
        void createEvent_Fails() {
            // Arrange
            LocalDate startDate = LocalDate.now().minusDays(1);
            String title = "Board Meeting";
            String requestBody = """
                    {
                        "event_title": "%s",
                        "event_start_date": "%s"
                    }
                    """.formatted(title, startDate);

            // Act
            webTestClient.post()
                    .uri(getBaseUrl())
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .body(Mono.just(requestBody), String.class)
                    .exchange()
                    .expectStatus().isBadRequest();
        }
    }

    @DisplayName("When: The start date is invalid")
    @Nested
    class WhenStartDateIsInvalid {

        // Wrong format or invalid date
        @ParameterizedTest
        @DisplayName("Then: The request fails with a 400 Bad Request status code")
        @ValueSource(strings = { "2022/01/01", "2022-13-01", "2022-02-30" })
        void createEvent_Fails(String invalidDate) {
            // Arrange
            String title = "Board Meeting";
            String requestBody = """
                    {
                        "event_title": "%s",
                        "event_start_date": "%s"
                    }
                    """.formatted(title, invalidDate);

            // Act
            webTestClient.post()
                    .uri(getBaseUrl())
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .body(Mono.just(requestBody), String.class)
                    .exchange()
                    .expectStatus().isBadRequest();
        }
    }

    @DisplayName("When: The duration is invalid")
    @Nested
    class WhenDurationIsInvalid {

        // Wrong format or invalid duration
        @ParameterizedTest
        @DisplayName("Then: The request fails with a 400 Bad Request status code")
        @ValueSource(strings = { "30", "2h30", "2h30m30s", "2.5h"})
        void createEvent_Fails(String invalidDuration) {
            // Arrange
            LocalDate startDate = LocalDate.now().plusDays(1);
            String title = "Board Meeting";
            String requestBody = """
                    {
                        "event_title": "%s",
                        "event_start_date": "%s",
                        "event_duration": "%s"
                    }
                    """.formatted(title, startDate, invalidDuration);

            // Act
            webTestClient.post()
                    .uri(getBaseUrl())
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .body(Mono.just(requestBody), String.class)
                    .exchange()
                    .expectStatus().isBadRequest();
        }
    }

    @DisplayName("When: the start time is provided without the duration")
    @Nested
    class WhenStartTimeIsProvidedWithoutDuration {

        @Test
        @DisplayName("Then: The request fails with a 400 Bad Request status code")
        void createEvent_Fails() {
            // Arrange
            LocalDate startDate = LocalDate.now().plusDays(1);
            String title = "Board Meeting";
            String startTime = "12:00:00";
            String requestBody = """
                    {
                        "event_title": "%s",
                        "event_start_date": "%s",
                        "event_start_time": "%s"
                    }
                    """.formatted(title, startDate, startTime);

            // Act
            webTestClient.post()
                    .uri(getBaseUrl())
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .body(Mono.just(requestBody), String.class)
                    .exchange()
                    .expectStatus().isBadRequest();
        }
    }

    @DisplayName("When: the duration is provided without the start time")
    @Nested
    class WhenDurationIsProvidedWithoutStartTime {

        @Test
        @DisplayName("Then: The request fails with a 400 Bad Request status code")
        void createEvent_Fails() {
            // Arrange
            LocalDate startDate = LocalDate.now().plusDays(1);
            String title = "Board Meeting";
            String duration = "2h30m";
            String requestBody = """
                    {
                        "event_title": "%s",
                        "event_start_date": "%s",
                        "event_duration": "%s"
                    }
                    """.formatted(title, startDate, duration);

            // Act
            webTestClient.post()
                    .uri(getBaseUrl())
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .body(Mono.just(requestBody), String.class)
                    .exchange()
                    .expectStatus().isBadRequest();
        }
    }
}