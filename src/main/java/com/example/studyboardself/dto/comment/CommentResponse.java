package com.example.studyboardself.dto.comment;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        Long postId,
        String content,
        String author,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
