package ru.practicum.ewmservice.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.event.EventRepository;
import ru.practicum.ewmservice.exception.BadRequestException;
import ru.practicum.ewmservice.exception.NotFoundException;
import ru.practicum.ewmservice.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public CommentDto create(CommentNewDto commentNew, int userId, int eventId) {
        Comment comment = new Comment();
        comment.setCreatedOn(LocalDateTime.now());
        comment.setAuthor(userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format(
                "User with id: %s not found", userId))));
        comment.setEvent(eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(String.format(
                "Event with id: %s not found", eventId))));
        comment.setInform(commentNew.getText());

        return CommentMapper.toCommentDto(commentRepository.save(comment));
    }

    public CommentDto update(CommentNewDto commentNew, int userId, boolean isAdmin) {
        Comment comment = commentRepository.findById(commentNew.getId()).orElseThrow(
                () -> new NotFoundException(
                        String.format("Comment with id: %s not found", commentNew.getId())));
        if (!isAdmin) {
            if (comment.getAuthor().getId() != userId) {
                throw new BadRequestException(String.format("User with id: %s didn't leave this comment", userId));
            }
        }
        comment.setInform(commentNew.getText());

        return CommentMapper.toCommentDto(commentRepository.save(comment));
    }


    public List<CommentDto> getByEvent(int eventId, Pageable pageable) {
        return commentRepository.findByEvent_Id(eventId, pageable).stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    public void delete(int commentId) {
        commentRepository.deleteById(commentId);
    }
}