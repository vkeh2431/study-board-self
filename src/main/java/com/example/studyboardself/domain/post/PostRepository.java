package com.example.studyboardself.domain.post;

import com.example.studyboardself.dto.post.PostListResponse;
import com.example.studyboardself.dto.post.PostSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<PostListResponse> search(PostSearchCondition condition, Pageable pageable);
}
