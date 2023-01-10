package ru.practicum.ewmservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Violation {

    private final String field;
    private final String message;
    private final String status;

}

