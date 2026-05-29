package com.example.studyboardself.dto.post;

//import com.example.studyboardself.domain.post.Post;

import java.time.LocalDateTime;

public record PostResponse(
        Long id,
        String title,
        String content,
        String author,
        int viewCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
