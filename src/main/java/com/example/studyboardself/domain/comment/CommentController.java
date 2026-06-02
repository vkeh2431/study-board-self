package com.example.studyboardself.domain.comment;

import com.example.studyboardself.dto.comment.CommentCreateRequest;
import com.example.studyboardself.dto.comment.CommentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/api/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> findByPostId(@PathVariable Long postId) {
        List<CommentResponse> responses = commentService.findByPostId(postId);
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/api/posts/{postId}/comments")
    public ResponseEntity<CommentResponse> create(
            @PathVariable Long postId,
            @Valid @RequestBody CommentCreateRequest request
    ) {
        CommentResponse response = commentService.create(postId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
