package com.example.studyboardself.dto.post;

import com.example.studyboardself.domain.post.Post;

import java.time.LocalDateTime;

public record PostListResponse(
        Long id,
        String title,
        String author,
        int viewCount,
        LocalDateTime creatdAt
) {
    public static PostListResponse from(Post post) {
        return new PostListResponse(
                post.getId(),
                post.getTitle(),
                post.getAuthor(),
                post.getViewCount(),
                post.getCreatedAt()
        );
    }
}
