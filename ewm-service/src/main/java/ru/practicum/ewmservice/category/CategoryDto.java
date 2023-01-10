package ru.practicum.ewmservice.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDto {
    private int id;

    @NotEmpty
    private String name;
}
