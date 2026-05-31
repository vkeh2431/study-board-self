package com.example.studyboardself.domain.post;

import com.example.studyboardself.dto.post.PostCreateRequest;
//import com.example.studyboardself.dto.post.PostListResponse;
import com.example.studyboardself.dto.post.PostResponse;
//import com.example.studyboardself.dto.post.PostUpdateRequest;
//import com.example.studyboardself.global.exception.ResourceNotFoundException;
import com.example.studyboardself.dto.post.PostUpdateRequest;
import com.example.studyboardself.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private Post createPost(String title, String content, String author) {
        return Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }

    @Transactional
    public PostResponse create(PostCreateRequest request) {
        Post post = createPost(request.title(), request.content(), request.author());
        Post saved = postRepository.save(post);

        return PostResponse.from(saved);
    }

    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", id));
        post.incrementViewCount();
        return PostResponse.from(post);
    }

    public PostResponse update(Long id, PostUpdateRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", id));
        post.update(request.title(), request.content());
        return PostResponse.from(post);
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", id));

        postRepository.delete(post);
    }

}
