package com.example.studyboardself.dto.post;


import java.time.LocalDateTime;

public record PostResponse(
        Long id,
        String title,
        String content,
        String authorName,
        int viewCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
