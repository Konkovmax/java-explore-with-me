package ru.practicum.ewmservice.request;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.event.Event;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {

    List<Request> findByRequesterId(int requesterId);

    List<Request> findByEventIn(List<Event> events);

}
