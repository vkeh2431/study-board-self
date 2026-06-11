package com.example.studyboardself.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PostCreateRequest(
        @NotBlank
        String title,

        @Size(max = 50000)
        String content,

        Long categoryId,

        List<String> tagNames
) {
}
