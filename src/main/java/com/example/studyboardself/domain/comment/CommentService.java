package com.example.studyboardself.domain.comment;

import com.example.studyboardself.dto.comment.CommentCreateRequest;
import com.example.studyboardself.dto.comment.CommentResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    public CommentResponse create(Long postId, CommentCreateRequest request) {
        return null;
    }

    public List<CommentResponse> findByPostId(Long postId) {
        return null;
    }
}
