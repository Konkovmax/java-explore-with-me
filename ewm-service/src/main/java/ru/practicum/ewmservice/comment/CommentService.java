package ru.practicum.ewmservice.comment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.category.Category;
import ru.practicum.ewmservice.event.*;
import ru.practicum.ewmservice.exception.NotFoundException;
import ru.practicum.ewmservice.request.Request;
import ru.practicum.ewmservice.request.RequestDto;
import ru.practicum.ewmservice.request.RequestMapper;
import ru.practicum.ewmservice.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository,
                          EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public CommentDto create(CommentNewDto commentNew, int userId, int eventId) {
        Comment comment = new Comment();
        comment.setCreatedOn(LocalDateTime.now());
        comment.setAuthor(userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format(
                "User with id: %s not found", userId))));
        comment.setEvent(eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(String.format(
                "Event with id: %s not found", eventId))));
        comment.setText(commentNew.getText());

        return CommentMapper.toCommentDto(commentRepository.save(comment));
    }

    public CommentDto update(CommentNewDto commentNew, int userId, int eventId, boolean isAdmin) {
        Comment comment = commentRepository.findById(commentNew.getId()).orElseThrow(
                () -> new NotFoundException(
                        String.format("Comment with id: %s not found", commentNew.getId())));
        comment.setText(commentNew.getText());

        return CommentMapper.toCommentDto(commentRepository.save(comment));
    }


    public List<CommentDto> getByEvent(int eventId) {
        List<Event> userEvents = eventRepository.getEventByInitiator_Id(userId);

        return requestRepository.findByEventIn(userEvents).stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    public void delete(int userId) {
        userRepository.deleteById(userId);
    }
}