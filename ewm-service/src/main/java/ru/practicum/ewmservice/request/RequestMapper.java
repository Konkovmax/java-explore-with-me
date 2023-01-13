package ru.practicum.ewmservice.request;

import ru.practicum.ewmservice.event.Event;
import ru.practicum.ewmservice.user.User;

public class RequestMapper {
    public static RequestDto toRequestDto(Request request) {
        return new RequestDto(
                request.getId(),
                request.getCreatedOn(),
                request.getRequester().getId(),
                request.getEvent().getId(),
                request.getStatus()
        );
    }

    public static Request toRequest(RequestDto request, User requester, Event event) {
        return new Request(
                request.getId(),
                request.getCreated(),
                requester,
                event,
                request.getStatus()
        );
    }
}
