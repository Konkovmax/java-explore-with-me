package ru.practicum.statsserver.hit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Views {
    private String app;
    private String uri;
    private int hits;
}
