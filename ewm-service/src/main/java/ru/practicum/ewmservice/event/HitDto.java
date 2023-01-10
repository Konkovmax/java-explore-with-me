package ru.practicum.ewmservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HitDto {
    private String app;
    private String uri;
    private String ip;
    private LocalDateTime timeStamp;
}
