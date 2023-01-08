package ru.practicum.ewmservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {
    private double lat;
    private double lon;
}
