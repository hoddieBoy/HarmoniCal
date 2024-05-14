package com.harmonical.backend.application.use_cases;

import com.harmonical.backend.domain.port.IEvent;
import com.harmonical.backend.domain.port.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateEventUseCaseTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private CreateEventUseCase createEventUseCase;

    private IEvent event;

    @BeforeEach
    void setup() {
        event = mock(IEvent.class);
    }

    @Test
    void shouldCreateEventWhenNoIdAndNotPast() {
        IEvent savedEvent = mock(IEvent.class);

        when(event.getId()).thenReturn(null);
        when(event.isPast()).thenReturn(false);
        when(savedEvent.getId()).thenReturn(UUID.randomUUID());

        when(eventRepository.save(event)).thenReturn(savedEvent);

        IEvent eventResult = createEventUseCase.execute(event);

        assertNotNull(eventResult, "Saved event should not be null");
        verify(eventRepository).save(event);
    }

    @Test
    void shouldThrowExceptionWhenEventAlreadyHasId() {
        UUID id = UUID.randomUUID();
        when(event.getId()).thenReturn(id);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createEventUseCase.execute(event),
                "Should throw an exception when ID is already set");

        assertEquals("Event ID must be null", exception.getMessage());
        verify(eventRepository, never()).save(event);
    }

    @Test
    void shouldNotCreatePastEvent() {
        when(event.getId()).thenReturn(null);
        when(event.isPast()).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createEventUseCase.execute(event),
                "Should throw an exception for past events");

        assertEquals("Event must not be in the past", exception.getMessage());
        verify(eventRepository, never()).save(event);
    }
}
