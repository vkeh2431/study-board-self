package com.example.studyboardself.domain.post;

import com.example.studyboardself.domain.category.CategoryRepository;
import com.example.studyboardself.domain.member.Member;
import com.example.studyboardself.domain.member.MemberRepository;
import com.example.studyboardself.domain.member.Role;
import com.example.studyboardself.domain.tag.TagRepository;
import com.example.studyboardself.global.config.JpaAuditingConfig;
import com.example.studyboardself.global.config.QueryDslConfig;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({JpaAuditingConfig.class, QueryDslConfig.class})
@ActiveProfiles("test")
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private EntityManager entityManager;

    private Member member;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(Member.builder()
                .email("author@example.como")
                .username("작성자")
                .password("encoded")
                .role(Role.USER)
                .build());
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    private Post createPost(String title, String content) {
        return Post.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();
    }

    @Test
    @DisplayName("게시글 저장")
    void save_post() {
        Post post = createPost("제목", "내용");

        Post saved = postRepository.save(post);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("제목");
        assertThat(saved.getContent()).isEqualTo("내용");
        assertThat(saved.getMember().getUsername()).isEqualTo("작성자");
        assertThat(saved.getViewCount()).isZero();
        assertThat(saved.getCreatedAt()).isNotNull();
    }

    @Test
    @DisplayName("인증 컨텍스트가 없으면 createdBy는 NULL")
    void save_post_without_authentication_leaves_createdBy_null() {
        Post saved = postRepository.saveAndFlush(createPost("제목", "내용"));

        assertThat(saved.getCreatedBy()).isNull();
    }

    @Test
    @DisplayName("인증된 사용자가 작성하면 createdBy에 memberId가 주입된다")
    void save_post_populates_createdBy_from_authentication() {
        
    }

    @Test
    @DisplayName("게시글 단건 조회")
    void findById_post() {

    }

    @Test
    @DisplayName("게시글 목록 조회")
    void findAll_posts() {

    }

    @Test
    @DisplayName("게시글 삭제 - soft delete: 조회에서는 제외되지만 행은 보존되고 deleted_at이 설정된다")
    void delete_post() {

    }

    @Test
    @DisplayName("제목으로 키워드 검색")
    void search_by_keyword_in_title() {

    }

    @Test
    @DisplayName("내용으로 키워드 검색")
    void search_by_keyword_in_content() {

    }

    @Test
    @DisplayName("작성자명으로 검색")
    void search_by_author() {

    }

    @Test
    @DisplayName("키워드 + 작성자 조건을 함께(AND) 적용 - 한쪽만 만족하는 글은 모두 제외된다")
    void search_by_keyword_and_author() {

    }

    @Test
    @DisplayName("조건이 없으면 전체 조회")
    void search_without_condition_returns_all() {

    }

    @Test
    @DisplayName("검색 결과에 댓글 수가 집계된다 (COUNT 서브쿼리 projection)")
    void search_aggregates_comment_count() {

    }

    @Test
    @DisplayName("카테고리로 검색하면 해당 카테고리 글만 나오고 결과에 카테고리명이 담긴다")
    void search_by_category() {

    }

    @Test
    @DisplayName("카테고리 없는 글도 전체 조회에 포함된다 (LEFT JOIN 검증)")
    void search_includes_post_without_category() {

    }

    @Test
    @DisplayName("태그명으로 검색")
    void search_by_tag() {

    }

    @Test
    @DisplayName("여러 태그를 가진 글도 태그 검색 시 한 건으로 집계된다 (distinct)")
    void search_by_tag_no_duplicate() {

    }

    @Test
    @DisplayName("키워드 검색 결과 없음")
    void search_returns_empty_when_no_match() {

    }

    @Test
    @DisplayName("페이징 동작 확인")
    void findAll_with_pageable() {

    }

    @Test
    @DisplayName("허용된 정렬 키(viewCount)로 동적 정렬된다")
    void search_sorts_by_allowed_view_count() {

    }

    @Test
    @DisplayName("화이트리스트 밖 정렬 키(member.password 등)는 무시하고 기본 정렬(createdAt DESC)로 폴백한다 (정렬 주입 방지)")
    void search_ignores_disallowed_sort_property() {

    }
}
