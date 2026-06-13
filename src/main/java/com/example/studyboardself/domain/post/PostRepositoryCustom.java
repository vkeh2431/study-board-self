package com.example.studyboardself.domain.post;

import com.example.studyboardself.dto.post.PostListResponse;
import com.example.studyboardself.dto.post.PostSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    Page<PostListResponse> search(PostSearchCondition condition, Pageable pageable);

    int incrementViewCount(Long id);
}
