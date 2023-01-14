package ru.practicum.ewmservice.comment;

public class CommentMapper {
    public static CommentDto toCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getCreatedOn(),
                comment.getAuthor().getId(),
                comment.getEvent().getId(),
                comment.getText()
        );
    }

}
