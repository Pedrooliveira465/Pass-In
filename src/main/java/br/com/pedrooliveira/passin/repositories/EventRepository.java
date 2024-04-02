package br.com.pedrooliveira.passin.repositories;

import br.com.pedrooliveira.passin.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, String> {}
