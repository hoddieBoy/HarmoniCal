package com.harmonical.backend.domain.model;

import com.harmonical.backend.domain.exception.NotScheduledEventException;
import com.harmonical.backend.domain.port.IEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TimeConstraintTest {

    @DisplayName("Given: A non-null event")
    @Nested
    class NonNullEvent {

        IEvent event;
        LocalDateTime beginTime;
        LocalDateTime endTime;

        @BeforeEach
        void setUp() {
            event = new Event(
                    "Test Event",
                    "Test Description",
                    LocalDate.now().plusDays(1),
                    LocalTime.NOON,
                    Duration.ofHours(2),
                    "Test Location"
            );
            beginTime = LocalDateTime.of(event.getDate(), LocalTime.NOON.minusHours(1));
            endTime = LocalDateTime.of(event.getDate(), LocalTime.NOON.plusHours(1));
        }

        @DisplayName("When: A valid begin and end time are provided")
        @Nested
        class ValidBeginAndEndTime {

            @Test
            @DisplayName("Then: A TimeConstraint should be created")
            void shouldCreateTimeConstraint() {
                TimeConstraint timeConstraint = new TimeConstraint(event, beginTime, endTime);

                assertNotNull(timeConstraint);
                assertEquals(event, timeConstraint.getEvent());
                assertEquals(beginTime, timeConstraint.getBeginTime());
                assertEquals(endTime, timeConstraint.getEndTime());
            }
        }

        @Nested
        @DisplayName("And: isSatisfied is called with no time")
        class IsSatisfiedWithNoTime {

            @Test
            @DisplayName("Then: NotScheduledEventException should be thrown")
            void shouldThrowNotScheduledException() {
                TimeConstraint timeConstraint = new TimeConstraint(event, beginTime, endTime);
                Map<IEvent, LocalDateTime> schedule = Map.of();
                assertThrows(NotScheduledEventException.class, () -> timeConstraint.isSatisfiedBy(schedule));
            }
        }

        @Nested
        @DisplayName("And: isSatisfied is called with a time")
        class IsSatisfiedWithTime {

            TimeConstraint timeConstraint;
            Map<IEvent, LocalDateTime> schedule;

            @BeforeEach
            void setUp() {
                timeConstraint = new TimeConstraint(event, beginTime, endTime);
                schedule = new HashMap<>();
            }

            @Nested
            @DisplayName("And: the time is within the constraint")
            class TimeWithinConstraint {

                @Test
                @DisplayName("Then: true should be returned")
                void shouldReturnTrue() {
                    schedule.put(event, LocalDateTime.of(event.getDate(), LocalTime.NOON));
                    assertTrue(timeConstraint.isSatisfiedBy(schedule));
                }
            }

            @Nested
            @DisplayName("And: the time is not within the constraint")
            class TimeNotWithinConstraint {

                @Test
                @DisplayName("Then: false should be returned")
                void shouldReturnFalse() {
                    schedule.put(event, LocalDateTime.of(event.getDate(), LocalTime.NOON.minusHours(2)));
                    assertFalse(timeConstraint.isSatisfiedBy(schedule));
                }
            }
        }

        @DisplayName("When: A begin time is after the end time")
        @Nested
        class BeginTimeAfterEndTime {

            @Test
            @DisplayName("Then: An IllegalArgumentException should be thrown")
            void shouldThrowIllegalArgumentException() {
                LocalDateTime invalidBeginTime = LocalDateTime.of(event.getDate(), LocalTime.NOON.plusHours(1));
                LocalDateTime invalidEndTime = LocalDateTime.of(event.getDate(), LocalTime.NOON);
                assertThrows(IllegalArgumentException.class, () -> new TimeConstraint(event, invalidBeginTime, invalidEndTime));
            }
        }
    }

    @DisplayName("Given: A null event")
    @Nested
    class NullEvent {

        IEvent event;
        LocalDateTime beginTime;
        LocalDateTime endTime;

        @BeforeEach
        void setUp() {
            event = null;
            beginTime = LocalDateTime.now();
            endTime = LocalDateTime.now().plusHours(1);
        }

        @DisplayName("Then: A NullPointerException should be thrown")
        @Test
        void shouldThrowNullPointerException() {
            assertThrows(NullPointerException.class, () -> new TimeConstraint(event, beginTime, endTime));
        }
    }
}