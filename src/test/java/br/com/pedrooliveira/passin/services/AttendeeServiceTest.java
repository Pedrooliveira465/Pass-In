package br.com.pedrooliveira.passin.services;

import br.com.pedrooliveira.passin.domain.attendee.Attendee;
import br.com.pedrooliveira.passin.domain.attendee.exceptions.AttendeeAlreadyExistException;
import br.com.pedrooliveira.passin.repositories.AttendeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AttendeeServiceTest {

    @Mock
    private AttendeeRepository attendeeRepository;

    @InjectMocks
    private AttendeeService attendeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return a list of attendees")
    void testGetAllAttendeesFromEvent() {
        // Mocking data
        Attendee attendee1 = new Attendee("1", "John Doe", "john@example.com", null, LocalDateTime.now());
        Attendee attendee2 = new Attendee("2", "Jane Doe", "jane@example.com", null, LocalDateTime.now());
        List<Attendee> attendees = Arrays.asList(attendee1, attendee2);

        when(attendeeRepository.findByEventId("eventId")).thenReturn(attendees);

        List<Attendee> result = attendeeService.getAllAttendeesFromEvent("eventId");

        assertEquals(2, result.size());
        assertEquals(attendees, result);
    }

    @Test
    @DisplayName("Should verify if attendee already exist")
    void testVerifyAttendeeSubscription_ThrowsAttendeeAlreadyExistException() {
        Attendee attendee = new Attendee("1", "John Doe", "john@example.com", null, LocalDateTime.now());
        when(attendeeRepository.findByEventIdAndEmail("eventId", "john@example.com")).thenReturn(Optional.of(attendee));

        assertThrows(AttendeeAlreadyExistException.class, () -> {
            attendeeService.verifyAttendeeSubscription("john@example.com", "eventId");
        });
    }
}
