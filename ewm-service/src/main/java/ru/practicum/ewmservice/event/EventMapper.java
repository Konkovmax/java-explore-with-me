package ru.practicum.ewmservice.event;


import ru.practicum.ewmservice.category.Category;
import ru.practicum.ewmservice.user.User;

import java.time.LocalDateTime;

public class EventMapper {
    public static EventFullDto toEventFullDto(Event event) {
        Location location = new Location(event.getLocationLat(), event.getLocationLon());

        return new EventFullDto(
                event.getId(),
                event.getTitle(),
                event.getAnnotation(),
                event.getDescription(),
                event.getCategory(),
                event.getCreatedOn(),
                event.getEventDate(),
                event.getInitiator(),
                location,
                event.isPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn(),
                event.isRequestModeration(),
                event.getState(),
                5,
                5

        );
    }

    public static Event toEvent(NewEventDto event, User user, Category category) {
        return new Event(
                event.getEventId(),
                event.getTitle(),
                event.getAnnotation(),
                event.getDescription(),
                category,
                LocalDateTime.now(),
                event.getEventDate(),
                user,
                event.getLocation().getLon(),
                event.getLocation().getLat(),
                event.isPaid(),
                event.getParticipantLimit(),
                null,
                event.isRequestModeration(),
                State.PENDING

        );
    }
}
