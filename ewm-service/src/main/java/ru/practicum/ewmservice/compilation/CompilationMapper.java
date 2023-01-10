package ru.practicum.ewmservice.compilation;

import ru.practicum.ewmservice.event.Event;

import java.util.Set;
import java.util.stream.Collectors;

public class CompilationMapper {
    public static CompilationDto toCompilationDto(Compilation compilation) {
        return new CompilationDto(
                compilation.getId(),
                compilation.getTitle(),
                compilation.isPinned(),
                compilation.getEvents().stream()
                        .map(i -> i.getId())
                        .collect(Collectors.toSet())
        );
    }

    public static Compilation toCompilation(CompilationDto compilation, Set<Event> events) {
        return new Compilation(
                compilation.getId(),
                compilation.getTitle(),
                compilation.isPinned(),
                events
        );
    }

    public static Compilation toCompilationNew(NewCompilationDto compilation, Set<Event> events) {
        return new Compilation(
                compilation.getId(),
                compilation.getTitle(),
                compilation.isPinned(),
                events
        );
    }
}
