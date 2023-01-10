package ru.practicum.ewmservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewmservice.category.Category;

@AllArgsConstructor
@Data
public class EventShortDto {
    private int id;
    private String title;
    private String annotation;
    private String description;
    private Category category;
    private boolean requestModeration;
    private State state;
}

