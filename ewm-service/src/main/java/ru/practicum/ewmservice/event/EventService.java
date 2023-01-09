package ru.practicum.ewmservice.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.category.Category;
import ru.practicum.ewmservice.category.CategoryRepository;
import ru.practicum.ewmservice.exception.NotFoundException;
import ru.practicum.ewmservice.user.User;
import ru.practicum.ewmservice.user.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventClient eventClient;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public EventService(EventRepository eventRepository, CategoryRepository categoryRepository,
                        UserRepository userRepository, EventClient eventClient) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.eventClient = eventClient;
    }


    public EventFullDto create(int userId, NewEventDto event) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format(
                "User with id: %s not found", userId)));
        Category category = categoryRepository.findById(event.getCategory()).orElseThrow(() -> new NotFoundException(
                String.format("Category with id: %s not found", event.getCategory())));
        return EventMapper.toEventFullDto(eventRepository.save(EventMapper.toEvent(event, user, category)));
    }

    public EventFullDto update(int userId, NewEventDto updateEvent) {
        Event event = eventRepository.findById(updateEvent.getEventId()).orElseThrow(() -> new NotFoundException(
                String.format("Event with id: %s not found", updateEvent.getEventId())));
        Category category = categoryRepository.findById(updateEvent.getCategory()).orElseThrow(
                () -> new NotFoundException(String.format("Category with id: %s not found", updateEvent.getCategory())));
        if ((event.getState() != State.PUBLISHED) && (event.getEventDate().minusHours(2).isAfter(LocalDateTime.now()))){
            if (event.getState() == State.CANCELED) {
                event.setState(State.PENDING);
            }
            EventPartialUpdateMapper mapper = new EventPartialUpdateMapperImpl();
            event = mapper.partialUpdate(updateEvent);
            event.setCategory(category);
        }
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    public EventFullDto updateAdmin(int eventId, NewEventDto updateEvent) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(
                String.format("Event with id: %s not found", eventId)));
        Category category = categoryRepository.findById(updateEvent.getCategory()).orElseThrow(
                () -> new NotFoundException(
                        String.format("Category with id: %s not found", updateEvent.getCategory())));
        EventPartialUpdateMapper mapper = new EventPartialUpdateMapperImpl();
        event = mapper.partialUpdate(updateEvent);
        event.setCategory(category);
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    public EventFullDto publish(int eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(String.format(
                "Event with id: %s not found", eventId)));
        event.setState(State.PUBLISHED);
        event.setPublishedOn(LocalDateTime.now());
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    public EventFullDto cancel(int userId, int eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(String.format(
                "Event with id: %s not found", eventId)));
        if (event.getState() == State.PENDING) {
            event.setState(State.CANCELED);
        }
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    public EventFullDto reject(int eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(String.format(
                "Event with id: %s not found", eventId)));
        if (event.getState() == State.PENDING) {
            event.setState(State.CANCELED);
        }
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    public List<EventFullDto> getAllParam(Integer[] users, String[] states, Integer[] categories,
                                          String rangeStart, String rangeEnd, Pageable pageable) {
        State[] enumStates = null;
        if (!(states == null)) {
            enumStates = Arrays.stream(states)
                    .map(i -> State.valueOf(i))
                    .toArray(State[]::new);
        }
        return eventRepository.findEventByParam(users, enumStates, categories,
                        pageable).stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    public List<EventFullDto> getAll(String text, Integer[] categories, boolean paid,
                                     String rangeStart, String rangeEnd, boolean onlyAvailable, String sort,
                                     Pageable pageable, HttpServletRequest request) {
        eventClient.createHit(new HitDto("ewm-main-service", request.getRequestURI(), request.getRemoteAddr(),
                LocalDateTime.now()));
        return eventRepository.searchEvent(text, categories, paid, pageable).stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    public List<EventFullDto> getAllByUser(int userId, Pageable pageable) {
        return eventRepository.getEventByInitiator_Id(userId, pageable).stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    public EventFullDto get(int eventId, HttpServletRequest request) {
        eventClient.createHit(new HitDto("ewm-main-service", request.getRequestURI(), request.getRemoteAddr(),
                LocalDateTime.now()));
        return EventMapper.toEventFullDto(eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException(String.format("Event with id: %s not found", eventId))));
    }

    public EventFullDto getByUser(int userId, int eventId) {
        return EventMapper.toEventFullDto(eventRepository.findEventByIdAndInitiatorId(eventId, userId)
        );
    }
}
