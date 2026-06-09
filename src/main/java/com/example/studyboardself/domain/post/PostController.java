package com.example.studyboardself.domain.post;

import com.example.studyboardself.dto.post.PostCreateRequest;
import com.example.studyboardself.dto.post.PostListResponse;
import com.example.studyboardself.dto.post.PostResponse;
import com.example.studyboardself.dto.post.PostSearchCondition;
import com.example.studyboardself.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "게시글", description = "게시글 CRUD 및 검색. 조회는 공개, 쓰기는 인증 필요")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<Page<PostListResponse>> findAll(
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        PostSearchCondition condition = new PostSearchCondition(keyword);
        Page<PostListResponse> responses = postService.findAll(condition, pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findById(
            @PathVariable Long id
    ) {
        PostResponse response = postService.findById(id, null);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PostResponse> create(
            @AuthenticationPrincipal CustomUserDetails principal,
            @Valid @RequestBody PostCreateRequest request
    ) {
        PostResponse response = postService.create(principal.getMemberId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<PostListResponse>> findPopular() {
        return ResponseEntity.ok(postService.findPopular());
    }

}
