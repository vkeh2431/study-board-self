package com.example.studyboardself.dto.post;

import jakarta.validation.constraints.NotBlank;

public record PostUpdateRequest(
        @NotBlank
        String title,

        String content
) {
}
