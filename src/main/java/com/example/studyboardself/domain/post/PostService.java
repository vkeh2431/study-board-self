package com.example.studyboardself.domain.post;

import com.example.studyboardself.domain.member.Role;
import com.example.studyboardself.dto.post.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    public PostResponse findById(Long id, Long memberId) {
        return null;
    }

    public Page<PostListResponse> findAll(PostSearchCondition postSearchCondition, Pageable pageable) {
        return null;
    }

    public PostResponse create(Long memberId, PostCreateRequest request) {
        return null;
    }

    public List<PostListResponse> findPopular() {
        return null;
    }

    public PostResponse update(Long id, Long memberId, Role role, PostUpdateRequest request) {
        return null;
    }
}
