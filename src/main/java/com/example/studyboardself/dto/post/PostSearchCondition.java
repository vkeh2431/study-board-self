package com.example.studyboardself.dto.post;

public record PostSearchCondition(
        String keyword,
        String author,
        Long categoryId,
        String tag
) {
}
