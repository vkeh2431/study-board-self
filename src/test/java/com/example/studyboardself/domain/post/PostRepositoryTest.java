package com.example.studyboardself.domain.post;

import com.example.studyboardself.global.config.JpaAuditingConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaAuditingConfig.class)
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    private Post createPost(String title, String contnent, String author) {
        return Post.builder()
                .title(title)
                .content(contnent)
                .author(author)
                .build();
    }


    @Test
    @DisplayName("게시글 저장")
    void save_post() {
        Post post = createPost("제목", "내용", "작성자");

        Post saved = postRepository.save(post);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("제목");
        assertThat(saved.getContent()).isEqualTo("내용");
        assertThat(saved.getAuthor()).isEqualTo("작성자");
        assertThat(saved.getViewCount()).isZero();
        assertThat(saved.getCreatedAt()).isNotNull();
    }

    @Test
    @DisplayName("게시글 단건 조회")
    void findById_post() {
        Post saved = postRepository.save(createPost("제목", "내용", "작성자"));

        Optional<Post> found = postRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("제목");
        assertThat(found.get().getContent()).isEqualTo("내용");
        assertThat(found.get().getAuthor()).isEqualTo("작성자");
    }

    @Test
    @DisplayName("게시글 목록 조회")
    void findAll_posts() {
        
    }

    @Test
    @DisplayName("게시글 삭제")
    void delete_post() {

    }
}
