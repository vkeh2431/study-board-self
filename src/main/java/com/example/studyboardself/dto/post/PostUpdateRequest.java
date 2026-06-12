package com.example.studyboardself.dto.post;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record PostUpdateRequest(
        @NotBlank
        String title,

        String content,

        Long categoryId,

        List<String> tagNames
) {
}
