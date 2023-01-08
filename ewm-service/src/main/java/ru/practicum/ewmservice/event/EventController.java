package ru.practicum.ewmservice.event;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping(path = "/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping()
    public List<EventFullDto> getAll(@RequestParam(value = "text", required = false) String text,
                                     @RequestParam(value = "categories", required = false) Integer[] categories,
                                     @RequestParam(value = "paid", required = false) boolean paid,
                                     @RequestParam(value = "rangestart", required = false) String rangeStart,
                                     @RequestParam(value = "rangeend", required = false) String rangeEnd,
                                     @RequestParam(value = "onlyAvailable", defaultValue = "false",
                                             required = false) boolean onlyAvailable,
                                     @RequestParam(value = "sort", required = false) String sort,
                                     @RequestParam(value = "from", defaultValue = "0", required = false) int from,
                                     @RequestParam(value = "size", defaultValue = "10", required = false) int size,
                                     HttpServletRequest request) {
        return eventService.getAll(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort,
                PageRequest.of((size > from) ? 0 : from / size, size), request);
    }

    @GetMapping("/{id}")
    public EventFullDto get(@PathVariable("id") Integer eventId, HttpServletRequest request) {
        return eventService.get(eventId, request);
    }

}
