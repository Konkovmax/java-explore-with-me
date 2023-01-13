package ru.practicum.ewmservice.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.event.Event;
import ru.practicum.ewmservice.request.Request;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Request> findByRequesterId(int requesterId);

    List<Request> findByEventIn(List<Event> events);

}
