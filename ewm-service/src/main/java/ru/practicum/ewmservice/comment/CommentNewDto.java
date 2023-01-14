package ru.practicum.ewmservice.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentNewDto {
    private int id;
    private String text;
}
