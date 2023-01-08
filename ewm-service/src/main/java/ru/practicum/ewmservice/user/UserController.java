package ru.practicum.ewmservice.user;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.event.EventFullDto;
import ru.practicum.ewmservice.event.EventService;
import ru.practicum.ewmservice.event.NewEventDto;
import ru.practicum.ewmservice.request.RequestDto;
import ru.practicum.ewmservice.request.RequestService;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final EventService eventService;
    private final RequestService requestService;

    public UserController(RequestService requestService, EventService eventService) {
        this.eventService = eventService;
        this.requestService = requestService;
    }

    @PostMapping("/{userid}/events")
    public EventFullDto createEvent(
            @Valid @RequestBody NewEventDto event,
            @PathVariable("userid") Integer userId) {
        return eventService.create(userId, event);
    }

    @PatchMapping("/{userid}/events")
    public EventFullDto updateEvent(
            @Valid @RequestBody NewEventDto event,
            @PathVariable("userid") Integer userId) {
        return eventService.update(userId, event);
    }

    @PatchMapping("/{userid}/events/{eventid}")
    public EventFullDto cancelEvent(@PathVariable("userid") Integer userId,
                                    @PathVariable("eventid") Integer eventId) {
        return eventService.cancel(userId, eventId);
    }

    @PatchMapping("/{userid}/requests/{requestid}/cancel")
    public RequestDto cancelRequest(@PathVariable("userid") int userId,
                                    @PathVariable("requestid") int requestId) {
        return requestService.cancel(userId, requestId);
    }

    @PatchMapping("/{userid}/events/{eventid}/requests/{requestid}/reject")
    public RequestDto rejectRequest(@PathVariable("userid") int userId,
                                    @PathVariable("eventid") int eventId,
                                    @PathVariable("requestid") int requestId) {
        return requestService.reject(userId, requestId, eventId);
    }

    @PatchMapping("/{userid}/events/{eventid}/requests/{requestid}/confirm")
    public RequestDto confirmRequest(@PathVariable("userid") int userId,
                                     @PathVariable("eventid") int eventId,
                                     @PathVariable("requestid") int requestId) {
        return requestService.confirm(userId, requestId, eventId);
    }

    @PostMapping("/{userid}/requests")
    public RequestDto createRequest(@PathVariable("userid") Integer userId,
                                    @RequestParam(value = "eventId") int eventId) {
        return requestService.create(userId, eventId);
    }

    @GetMapping("/{id}/events")
    public List<EventFullDto> getAllUserEvents(@PathVariable("id") Integer userId,
                                               @RequestParam(value = "from", defaultValue = "0",
                                                       required = false) int from,
                                               @RequestParam(value = "size", defaultValue = "10",
                                                       required = false) int size) {
        return eventService.getAllByUser(userId,
                PageRequest.of((size > from) ? 0 : from / size, size));
    }

    @GetMapping("/{id}/requests")
    public List<RequestDto> getAllUserRequests(@PathVariable("id") Integer userId) {
        return requestService.getAll(userId);
    }

    @GetMapping("/{userid}/events/{eventid}")
    public EventFullDto getUserEvent(@PathVariable("userid") Integer userId,
                                     @PathVariable("eventid") int eventId) {
        return eventService.getByUser(userId, eventId);
    }

    @GetMapping("/{userid}/events/{eventid}/requests")
    public List<RequestDto> getUserEventsRequest(@PathVariable("userid") Integer userId,
                                                 @PathVariable("eventid") int eventId) {
        return requestService.getByUserEvent(userId, eventId);
    }

}
