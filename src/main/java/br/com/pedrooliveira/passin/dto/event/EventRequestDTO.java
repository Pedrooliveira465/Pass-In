package br.com.pedrooliveira.passin.dto.event;

public record EventRequestDTO(
        String title,
        String details,
        Integer maximumAttendees
) {}
