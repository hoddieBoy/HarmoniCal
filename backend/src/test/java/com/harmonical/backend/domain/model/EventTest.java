package com.harmonical.backend.domain.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

class EventTest {

    private Event event;
    private UUID validId;
    private LocalDate validDate;
    private LocalTime validTime;
    private Duration validDuration;
    private String validTitle;
    private String validDescription;
    private String validLocation;

    @BeforeEach
    void setUp() {
        validId = UUID.randomUUID();
        validDate = LocalDate.now();
        validTime = LocalTime.NOON;
        validDuration = Duration.ofHours(3);
        validTitle = "Board Meeting";
        validDescription = "Monthly strategic meeting";
        validLocation = "Conference Room A";
        event = new Event(validId, validTitle, validDescription, validDate, validTime, validDuration, validLocation);
    }

    @Test
    @DisplayName("Event constructor should initialize all fields correctly")
    void constructor_ShouldInitializeFieldsCorrectly() {
        assertAll("Constructor should set all fields correctly",
                () -> assertEquals(validId, event.getId()),
                () -> assertEquals(validTitle, event.getTitle()),
                () -> assertEquals(validDescription, event.getDescription()),
                () -> assertEquals(validDate, event.getDate()),
                () -> assertEquals(validTime, event.getTime()),
                () -> assertEquals(validDuration, event.getDuration()),
                () -> assertEquals(validLocation, event.getLocation())
        );
    }

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = { "  ", "\t", "\n" })
    @DisplayName("Event constructor should throw IllegalArgumentException when title is invalid")
    void constructor_ShouldThrowException_When_TitleIsInvalid(String invalidTitle) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                        new Event(validId, invalidTitle, validDescription, validDate, validTime, validDuration, validLocation),
                "Constructor should throw exception when title is invalid"
        );
        assertEquals("Title cannot be blank", exception.getMessage());
    }

    @Test
    @DisplayName("Reschedule method should update date, time, and duration correctly")
    void reschedule_ShouldUpdateCorrectly() {
        LocalDate newDate = LocalDate.now().plusDays(5);
        LocalTime newTime = LocalTime.of(14, 0);
        Duration newDuration = Duration.ofHours(4);
        event.reschedule(newDate, newTime, newDuration);
        assertAll("Reschedule should update date, time, and duration",
                () -> assertEquals(newDate, event.getDate()),
                () -> assertEquals(newTime, event.getTime()),
                () -> assertEquals(newDuration, event.getDuration())
        );
    }

    @Test
    @DisplayName("Reschedule should throw IllegalArgumentException if duration is negative")
    void reschedule_ShouldThrowException_When_EndTimeIsBeforeStartTime() {
        LocalDate newDate = LocalDate.now().plusDays(5);
        LocalTime newTime = LocalTime.of(14, 0);
        Duration newDuration = Duration.ofHours(-4);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> event.reschedule(newDate, newTime, newDuration));
        assertEquals("Duration cannot be negative", exception.getMessage());
    }

    @Test
    @DisplayName("UpdateDetails should use default values when description or location is null")
    void updateDetails_ShouldUseDefaultValues_When_NullPassed() {
        event.updateDetails(validTitle, null, null);
        assertAll("Should use default values for null description or location",
                () -> assertEquals(validTitle, event.getTitle()),
                () -> assertEquals("No description provided", event.getDescription()),
                () -> assertEquals("No location provided", event.getLocation())
        );
    }

    @Test
    @DisplayName("Event should correctly identify if it is past")
    void isPast_ShouldIdentifyPastCorrectly() {
        Event pastEvent = new Event(validId, "Past Event", "This event was in the past", LocalDate.now().minusDays(1), LocalTime.NOON, Duration.ofHours(1), "Old Room");
        assertTrue(pastEvent.isPast(), "Should return true for events that are in the past");
    }

    @Test
    @DisplayName("Event should correctly identify if it is not past")
    void isPast_ShouldIdentifyNotPastCorrectly() {
        Event futureEvent = new Event(validId, "Future Event", "This event is in the future", LocalDate.now().plusDays(1), LocalTime.NOON, Duration.ofHours(1), "Future Room");
        assertFalse(futureEvent.isPast(), "Should return false for events that are in the future");
    }

    @Test
    @DisplayName("Should throw NullPointerException when title is null")
    void should_ThrowException_When_TitleIsNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () ->
                        new Event(validId, null, validDescription, validDate, validTime, validDuration, validLocation),
                "Constructor should throw exception when title is null"
        );
        assertTrue(exception.getMessage().contains("Title cannot be null"), "Exception message should indicate null title");
    }

    // More tests can be added as necessary to cover additional methods and edge cases.
}
