package br.com.pedrooliveira.passin.controllers;

import br.com.pedrooliveira.passin.dto.attendee.AttendeeBadgeResponseDTO;
import br.com.pedrooliveira.passin.services.AttendeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/attendees")
@RequiredArgsConstructor
@Tag(name = "Attendee Controller", description = "Handle all operations related to Attendee")
public class AttendeeController {
    private final AttendeeService attendeeService;

    @GetMapping("/{attendeeId}/badge")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the attendee badge"),
            @ApiResponse(responseCode = "404", description = "Attendee not found"),
    })
    @Operation(summary = "Get Attendee Badge", description = "Get the badge for a specific attendee")
    public ResponseEntity<AttendeeBadgeResponseDTO> getAttendeeBadge(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder){
        AttendeeBadgeResponseDTO response = this.attendeeService.getAttendeeBadge(attendeeId, uriComponentsBuilder);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{attendeeId}/check-in")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Check-in successfully registered"),
            @ApiResponse(responseCode = "404", description = "Attendee not found"),
    })
    @Operation(summary = "Register Check-In", description = "Register a check-in for a specific attendee")
    public ResponseEntity registerCheckIn(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder){
        this.attendeeService.checkInAttendee(attendeeId);

        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeId).toUri();

        return ResponseEntity.created(uri).build();
    }
}
