package com.example.studyboardself.dto.comment;

public record CommentCreateRequest(
        String content,
        String author
) {

}
