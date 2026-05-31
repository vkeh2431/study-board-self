package com.example.studyboardself.domain;

import com.example.studyboardself.domain.post.Post;
import com.example.studyboardself.domain.post.PostRepository;
import com.example.studyboardself.domain.post.PostService;
import com.example.studyboardself.dto.post.PostCreateRequest;
import com.example.studyboardself.dto.post.PostResponse;
import com.example.studyboardself.dto.post.PostUpdateRequest;
import com.example.studyboardself.global.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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

    @Test
    @DisplayName("게시글 단건 조회 시 조회수 증가")
    void find_post_by_id() {
        Post post = createPost("제목", "내용", "작성자");

        given(postRepository.findById(1L)).willReturn(Optional.of(post));

        PostResponse response = postService.findById(1L);

        assertThat(response.title()).isEqualTo("제목");
        assertThat(response.viewCount()).isEqualTo(1);
        assertThat(post.getViewCount()).isEqualTo(1);
    }


    @Test
    @DisplayName("게시글 단건 조회 시 게시글이 없으면 예외 발생")
    void find_post_by_id_not_found() {
        given(postRepository.findById(999L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> postService.findById(999L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("게시글 수정")
    void update_post() {
        Post post = createPost("기존 제목", "기존 내용", "작성자");
        PostUpdateRequest request = new PostUpdateRequest("수정된 제목", "수정된 내용");

        given(postRepository.findById(1L)).willReturn(Optional.of(post));

        PostResponse response = postService.update(1L, request);

        assertThat(response.title()).isEqualTo("수정된 제목");
        assertThat(response.content()).isEqualTo("수정된 내용");
        assertThat(post.getTitle()).isEqualTo("수정된 제목");
    }

    @Test
    @DisplayName("게시글 수정 시 게시글이 없으면 예외 발생")
    void update_post_not_found() {
        PostUpdateRequest request = new PostUpdateRequest("수정된 제목", "수정된 내용");

        given(postRepository.findById(999L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> postService.update(999L, request))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("게시글 삭제")
    void delete_post() {
        Post post = createPost("제목", "내용", "작성자");

        given(postRepository.findById(1L)).willReturn(Optional.of(post));

        postService.delete(1L);

        verify(postRepository).delete(post);
    }

    @Test
    @DisplayName("게시글 삭제 시 게시글이 없으면 예외 발생")
    void delete_post_not_found() {
        given(postRepository.findById(999L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> postService.delete(999L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
