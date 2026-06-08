package com.example.studyboardself.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostCreateRequest(
        @NotBlank
        String title,

        @Size(max = 50000)
        String content
) {
}
