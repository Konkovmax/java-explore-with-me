package ru.practicum.ewmservice.compilations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.event.Event;
import ru.practicum.ewmservice.event.EventRepository;
import ru.practicum.ewmservice.exception.NotFoundException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    public CompilationService(CompilationRepository compilationRepository, EventRepository eventRepository) {
        this.compilationRepository = compilationRepository;
        this.eventRepository = eventRepository;
    }

    public Compilation create(CompilationDto compilationDto) {
        Set<Event> events = eventRepository.findByIdIn(compilationDto.getEvents());
        return compilationRepository.save(
                CompilationMapper.toCompilation(compilationDto, events));
    }

    public List<Compilation> getAll(Pageable pageable) {
        return compilationRepository.findAll(pageable).stream()
                .collect(Collectors.toList());
    }

    public Compilation addEvent(int compId, int eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException(String.format("Event with id: %s not found", eventId)));
        Compilation comp = compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException(String.format("Compilation with id: %s not found", compId)));
        Set<Event> events = comp.getEvents();
        events.add(event);
        comp.setEvents(events);
        return compilationRepository.save(comp);
    }

    public Compilation deleteEvent(int compId, int eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException(String.format("Event with id: %s not found", eventId)));
        Compilation comp = compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException(String.format("Compilation with id: %s not found", compId)));
        Set<Event> events = comp.getEvents();
        events.remove(event);
        comp.setEvents(events);
        return compilationRepository.save(comp);
    }


    public Compilation pin(int compId) {
        Compilation comp = compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException(String.format("Compilation with id: %s not found", compId)));
        comp.setPinned(true);
        return compilationRepository.save(comp);
    }

    public Compilation unpin(int compId) {
        Compilation comp = compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException(String.format("Compilation with id: %s not found", compId)));
        comp.setPinned(false);
        return compilationRepository.save(comp);
    }

    public void delete(int compId) {
        compilationRepository.deleteById(compId);
    }

    public Compilation get(int compilationId) {
        return compilationRepository.findById(compilationId).orElseThrow(
                () -> new NotFoundException(String.format("Compilation with id: %s not found", compilationId)));
    }
}
