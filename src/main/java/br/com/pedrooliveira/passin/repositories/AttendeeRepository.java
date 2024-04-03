package br.com.pedrooliveira.passin.repositories;

import java.util.List;
import java.util.Optional;

import br.com.pedrooliveira.passin.domain.attendee.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendeeRepository extends JpaRepository<Attendee, String>{

    List<Attendee> findByEventId(String eventId);
    Optional<Attendee> findByEventIdAndEmail(String eventId, String email);
}