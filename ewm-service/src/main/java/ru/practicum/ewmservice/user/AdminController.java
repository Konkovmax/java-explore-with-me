package ru.practicum.ewmservice.user;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.category.CategoryDto;
import ru.practicum.ewmservice.category.CategoryService;
import ru.practicum.ewmservice.comment.CommentDto;
import ru.practicum.ewmservice.comment.CommentNewDto;
import ru.practicum.ewmservice.comment.CommentService;
import ru.practicum.ewmservice.compilation.Compilation;
import ru.practicum.ewmservice.compilation.CompilationDto;
import ru.practicum.ewmservice.compilation.CompilationService;
import ru.practicum.ewmservice.event.EventFullDto;
import ru.practicum.ewmservice.event.EventService;
import ru.practicum.ewmservice.event.NewEventDto;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(path = "/admin")
public class AdminController {
    private final UserService userService;
    private final EventService eventService;
    private final CategoryService categoryService;
    private final CompilationService compilationService;
    private final CommentService commentService;

    public AdminController(UserService userService, EventService eventService,
                           CategoryService categoryService, CompilationService compilationService,
                           CommentService commentService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.eventService = eventService;
        this.compilationService = compilationService;
        this.commentService = commentService;
    }

    @PostMapping("/users")
    public UserDto create(@Valid @RequestBody UserDto user) {
        return userService.create(user);
    }

    @PatchMapping("/compilations/{compid}/events/{eventid}")
    public Compilation addEvent(@PathVariable("compid") int compId, @PathVariable("eventid") int eventId) {
        return compilationService.addEvent(compId, eventId);
    }

    @DeleteMapping("/compilations/{compid}/events/{eventid}")
    public Compilation deleteEvent(@PathVariable("compid") int compId, @PathVariable("eventid") int eventId) {
        return compilationService.deleteEvent(compId, eventId);
    }

    @PatchMapping("/compilations/{compid}/pin")
    public Compilation pin(@PathVariable("compid") int compId) {
        return compilationService.pin(compId);
    }

    @DeleteMapping("/compilations/{compid}/pin")
    public Compilation unpin(@PathVariable("compid") int compId) {
        return compilationService.unpin(compId);
    }

    @PostMapping("/compilations")
    public Compilation create(@Valid @RequestBody CompilationDto compilation) {
        return compilationService.create(compilation);
    }

    @PostMapping("/categories")
    public CategoryDto create(@Valid @RequestBody CategoryDto categoryDto) {
        return categoryService.create(categoryDto);
    }

    @PutMapping("/events/{eventid}")
    public EventFullDto update(@PathVariable("eventid") int eventId, @RequestBody NewEventDto event) {
        return eventService.updateAdmin(eventId, event);
    }

    @GetMapping("/users")
    public List<UserDto> getAllUsers(@RequestParam(value = "ids") Integer[] ids) {
        return userService.getAll(ids);
    }

    @GetMapping("/events")
    public List<EventFullDto> getAllEvents(@RequestParam(value = "users", required = false) Integer[] users,
                                           @RequestParam(value = "states", required = false) String[] states,
                                           @RequestParam(value = "categories", required = false) Integer[] categories,
                                           @RequestParam(value = "rangeStart", required = false) String rangeStart,
                                           @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
                                           @RequestParam(value = "from", defaultValue = "0", required = false) int from,
                                           @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        return eventService.getAllParam(users, states, categories, rangeStart, rangeEnd,
                PageRequest.of((size > from) ? 0 : from / size, size));
    }

    @PatchMapping("/categories")
    public CategoryDto update(@Valid @RequestBody CategoryDto category) {
        return categoryService.update(category);
    }

    @PatchMapping("/events/{id}/publish")
    public EventFullDto publishEvent(@PathVariable("id") int eventId) {
        return eventService.publish(eventId);
    }

    @PatchMapping("/events/{id}/reject")
    public EventFullDto rejectEvent(@PathVariable("id") int eventId) {
        return eventService.reject(eventId);
    }

    @DeleteMapping(value = "users/{id}")
    public void deleteUser(@PathVariable("id") Integer userId) {
        userService.delete(userId);
    }

    @DeleteMapping(value = "categories/{id}")
    public void deleteCategory(@PathVariable("id") Integer categoryId) {
        categoryService.delete(categoryId);
    }

    @DeleteMapping(value = "compilations/{id}")
    public void deleteCompilation(@PathVariable("id") Integer compId) {
        compilationService.delete(compId);
    }

    @PutMapping("/{userid}/events/{eventid}")
    public CommentDto update(@PathVariable("userid") int userId, @RequestBody CommentNewDto comment) {
        return commentService.update(comment, userId, true);
    }
}
