package ru.practicum.ewmservice.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewmservice.category.Category;
import ru.practicum.ewmservice.user.User;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class EventFullDto {
    private int id;
    private String title;
    private String annotation;
    private String description;
    private Category category;
    private LocalDateTime CreatedOn;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private User Initiator;
    private Location location;
    private boolean paid;
    private int participantLimit;
    private LocalDateTime publishedOn;
    private boolean requestModeration;
    private State state;
    private int confirmedRequests;
    private int views;
}

