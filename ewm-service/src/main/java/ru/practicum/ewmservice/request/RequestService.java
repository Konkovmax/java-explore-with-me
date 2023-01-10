package ru.practicum.ewmservice.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.event.Event;
import ru.practicum.ewmservice.event.EventRepository;
import ru.practicum.ewmservice.exception.NotFoundException;
import ru.practicum.ewmservice.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RequestService {
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public RequestService(RequestRepository requestRepository,
                          EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
    }

    public RequestDto create(int userId, int eventId) {
        Request request = new Request();
        request.setCreatedOn(LocalDateTime.now());
        request.setRequester(userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format(
                "User with id: %s not found", userId))));
        request.setEvent(eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(String.format(
                "Event with id: %s not found", eventId))));
        request.setStatus("PENDING");
        return RequestMapper.toRequestDto(requestRepository.save(request));
    }

    public List<RequestDto> getAll(int userId) {
        return requestRepository.findByRequesterId(userId).stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    public List<RequestDto> getByUserEvent(int userId, int eventId) {
        List<Event> userEvents = eventRepository.getEventByInitiator_Id(userId);
        return requestRepository.findByEventIn(userEvents).stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    public RequestDto cancel(int userId, int requestId) {
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new NotFoundException(String.format(
                "Event with id: %s not found", requestId)));
        request.setStatus("CANCELED");
        return RequestMapper.toRequestDto(requestRepository.save(request));
    }

    public RequestDto reject(int userId, int requestId, int eventId) {
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new NotFoundException(String.format(
                "Event with id: %s not found", requestId)));
        request.setStatus("REJECTED");
        return RequestMapper.toRequestDto(requestRepository.save(request));
    }

    public RequestDto confirm(int userId, int requestId, int eventId) {
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new NotFoundException(String.format(
                "Event with id: %s not found", requestId)));
        request.setStatus("CONFIRMED");
        return RequestMapper.toRequestDto(requestRepository.save(request));
    }

}
