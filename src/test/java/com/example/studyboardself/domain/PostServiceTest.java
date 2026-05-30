package com.example.studyboardself.domain;

import com.example.studyboardself.domain.post.Post;
import com.example.studyboardself.domain.post.PostRepository;
import com.example.studyboardself.domain.post.PostService;
import com.example.studyboardself.dto.post.PostCreateRequest;
import com.example.studyboardself.dto.post.PostResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    private Post createPost(String title, String content, String author) {
        return Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }

    @Test
    @DisplayName("게시글 생성")
    void create_post() {
        PostCreateRequest request = new PostCreateRequest("제목", "내용", "작성자");
        Post saved = createPost("제목", "내용", "작성자");

        given(postRepository.save(any(Post.class))).willReturn(saved);

        PostResponse response = postService.create(request);

        assertThat(response.title()).isEqualTo("제목");
        assertThat(response.content()).isEqualTo("내용");
        assertThat(response.author()).isEqualTo("작성자");
        verify(postRepository).save(any(Post.class));
    }
}
