package br.com.pedrooliveira.passin.controllers;

import br.com.pedrooliveira.passin.dto.attendee.AttendeeIdDTO;
import br.com.pedrooliveira.passin.dto.attendee.AttendeeRequestDTO;
import br.com.pedrooliveira.passin.dto.attendee.AttendeesListResponseDTO;
import br.com.pedrooliveira.passin.dto.event.EventIdDTO;
import br.com.pedrooliveira.passin.dto.event.EventRequestDTO;
import br.com.pedrooliveira.passin.dto.event.EventResponseDTO;
import br.com.pedrooliveira.passin.services.AttendeeService;
import br.com.pedrooliveira.passin.services.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Tag(name = "Event Controller", description = "Handle all operations related to Event")
public class EventController {
    private final EventService eventService;
    private final AttendeeService attendeeService;

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the event details"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
    })
    @Operation(summary = "Get Event Details", description = "Get details of a specific event by ID")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String id){
        EventResponseDTO event = this.eventService.getEventDetail(id);
        return ResponseEntity.ok(event);
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
    })
    @Operation(summary = "Create Event", description = "Create a new event")
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO body, UriComponentsBuilder uriComponentsBuilder){
        EventIdDTO eventIdDTO = this.eventService.createEvent(body);

        var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(eventIdDTO.eventId()).toUri();

        return ResponseEntity.created(uri).body(eventIdDTO);
    }

    @PostMapping("/{eventId}/attendees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Participant registered successfully"),
            @ApiResponse(responseCode = "404", description = "Event or attendee not found"),
    })
    @Operation(summary = "Register Participant", description = "Register a participant on a specific event")
    public ResponseEntity<AttendeeIdDTO> registerParticipant(@PathVariable String eventId, @RequestBody AttendeeRequestDTO body, UriComponentsBuilder uriComponentsBuilder){
        AttendeeIdDTO attendeeIdDTO = this.eventService.registerAttendeeOnEvent(eventId, body);

        var uri = uriComponentsBuilder.path("/attendees/{attendId}/badge").buildAndExpand(attendeeIdDTO.attendeeId()).toUri();

        return ResponseEntity.created(uri).body(attendeeIdDTO);
    }

    @GetMapping("/attendees/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of attendees"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
    })
    @Operation(summary = "Get Event Attendees", description = "Get the list of attendees for a specific event")
    public ResponseEntity<AttendeesListResponseDTO> getEventAttendee(@PathVariable String id){
        AttendeesListResponseDTO attendeesListResponse = this.attendeeService.getEventsAttendee(id);
        return ResponseEntity.ok(attendeesListResponse);
    }
}
