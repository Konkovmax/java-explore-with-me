package ru.practicum.ewmservice.comment;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{userid}/events/{eventid}")
    public CommentDto create(@PathVariable("userid") int userId, @PathVariable("eventid") int eventId,
                             @RequestBody CommentNewDto comment) {
        return commentService.create(comment, userId, eventId);
    }

    @DeleteMapping("/{commentid}")
    public void delete(@PathVariable("commentid") int commentId) {
        commentService.delete(commentId);
    }

    @PutMapping("/{userid}/events/{eventid}")
    public CommentDto update(@PathVariable("userid") int userId, @RequestBody CommentNewDto comment) {
        return commentService.update(comment, userId, false);
    }

    @GetMapping("/{eventid}")
    public List<CommentDto> getAllEvents(@PathVariable("eventid") int eventId,
                                         @RequestParam(value = "from", defaultValue = "0", required = false) int from,
                                         @RequestParam(value = "size", defaultValue = "10", required = false) int size,
                                         @RequestParam(value = "sortby", defaultValue = "createdOn",
                                                 required = false) String sortBy,
                                         @RequestParam(value = "sortorder", defaultValue = "desc",
                                                 required = false) String sortOrder) {
        return commentService.getByEvent(eventId, PageRequest.of((size > from) ? 0 : from / size, size,
                (sortOrder.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending())));
    }

}
