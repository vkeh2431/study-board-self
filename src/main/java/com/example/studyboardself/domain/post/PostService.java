package com.example.studyboardself.domain.post;

import com.example.studyboardself.dto.post.PostCreateRequest;
//import com.example.studyboardself.dto.post.PostListResponse;
import com.example.studyboardself.dto.post.PostResponse;
//import com.example.studyboardself.dto.post.PostUpdateRequest;
//import com.example.studyboardself.global.exception.ResourceNotFoundException;
import com.example.studyboardself.dto.post.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {

    public PostResponse create(PostCreateRequest request) {
        return null;
    }

    public PostResponse findById(Long id) {
        return null;
    }

    public PostResponse updatePost(Long id, PostUpdateRequest request) {
        return null;
    }

}
