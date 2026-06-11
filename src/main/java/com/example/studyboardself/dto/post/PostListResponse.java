package com.example.studyboardself.dto.post;

import java.time.LocalDateTime;

public record PostListResponse(
        Long id,
        String title,
        String authorName,
        String categoryName,
        int viewCount,
        long commentCount,
        long likeCount,
        LocalDateTime createdAt
) {

}
