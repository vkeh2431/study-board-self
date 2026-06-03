package com.example.studyboardself.domain.comment;

import com.example.studyboardself.domain.post.Post;
import com.example.studyboardself.domain.post.PostRepository;
import com.example.studyboardself.dto.comment.CommentCreateRequest;
import com.example.studyboardself.dto.comment.CommentResponse;
import com.example.studyboardself.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentResponse create(Long postId, CommentCreateRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", postId));
        Comment comment = Comment.builder()
                .content(request.content())
                .author(request.author())
                .build();
        post.addComment(comment);
        Comment saved = commentRepository.save(comment);
        return CommentResponse.from(saved);
    }

    public List<CommentResponse> findByPostId(Long postId) {
        postRepository.findById(postId)
                .orElseThrow();
        return commentRepository.findByPostIdOrderByCreatedAtDesc(postId).stream()
                .map(CommentResponse::from)
                .toList();
    }
}
