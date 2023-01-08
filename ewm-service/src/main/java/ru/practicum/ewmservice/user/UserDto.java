package ru.practicum.ewmservice.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    private int id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;
}
