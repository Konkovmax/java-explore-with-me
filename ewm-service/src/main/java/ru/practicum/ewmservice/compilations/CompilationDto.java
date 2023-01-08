package ru.practicum.ewmservice.compilations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CompilationDto {
    private int id;
    @NotEmpty
    private String title;
    private boolean pinned;
    private Set<Integer> events = new HashSet<>();
}
