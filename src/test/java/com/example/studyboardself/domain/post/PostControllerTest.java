package com.example.studyboardself.domain.post;


import com.example.studyboardself.dto.post.PostListResponse;
import com.example.studyboardself.dto.post.PostResponse;
import com.example.studyboardself.dto.post.PostSearchCondition;
import com.example.studyboardself.global.exception.ResourceNotFoundException;
import net.bytebuddy.implementation.bind.ParameterLengthResolver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import javax.xml.crypto.dsig.keyinfo.PGPData;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("게시글 단건 조회")
    void find_post_by_id() throws Exception {
        PostResponse response = new PostResponse(1L, "제목", "내용", "작성자", 1,
                LocalDateTime.now(), LocalDateTime.now());

        given(postService.findById(eq(1L), any())).willReturn(response);

        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("제목"));
    }

    @Test
    @DisplayName("게시글 단건 조회 시 게시글이 없으면 404")
    void find_post_by_id_not_found() throws Exception {
        given(postService.findById(eq(999L), any()))
                .willThrow(new ResourceNotFoundException("Post", 999L));

        mockMvc.perform(get("/api/posts/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("RESOURCE_NOT_FOUND"));
    }

    @Test
    @DisplayName("기본 페이징으로 게시글 목록 조회")
    void findAll_with_default_pagination() throws Exception {
        PageRequest pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<PostListResponse> content = List.of(
                new PostListResponse(1L, "Spring", "작성자", 0, LocalDateTime.now())
        );
        Page<PostListResponse> page = new PageImpl<>(content, pageable, 1);

        given(postService.findAll(any(PostSearchCondition.class), any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].title").value("Spring"))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1));

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(postService).findAll(any(PostSearchCondition.class), pageableCaptor.capture());
        Pageable captured = pageableCaptor.getValue();
        assertThat(captured.getPageNumber()).isZero();
        assertThat(captured.getPageSize()).isEqualTo(10);
        Sort.Order order = captured.getSort().getOrderFor("createdAt");
        assertThat(order).isNotNull();
        assertThat(order.getDirection()).isEqualTo(Sort.Direction.DESC);
    }

    @Test
    @DisplayName("키워드로 게시글 검색")
    void findAll_with_keyword() throws Exception {
        PageRequest pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<PostListResponse> content = List.of(
                new PostListResponse(1L, "Spring", "작성자", 0, LocalDateTime.now())
        );
        Page<PostListResponse> page = new PageImpl<>(content, pageable, 1);

        given(postService.findAll(any(PostSearchCondition.class), any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/api/posts").param("keyword", "Spring"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Spring"))
                .andExpect(jsonPath("$.totalElements").value(1));

        ArgumentCaptor<PostSearchCondition> postSearchConditionCaptor = ArgumentCaptor.forClass(PostSearchCondition.class);
        verify(postService).findAll(postSearchConditionCaptor.capture(), any(Pageable.class));
        PostSearchCondition captured = postSearchConditionCaptor.getValue();
        assertThat(captured.keyword()).isEqualTo("Spring");
    }

    @Test
    @DisplayName("커스텀 페이지와 사이즈로 조회")
    void findAll_with_custom_page_and_size() throws Exception {
        PageRequest pageable = PageRequest.of(1, 5, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<PostListResponse> content = List.of(
                new PostListResponse(6L, "제목6", "작성자", 0, LocalDateTime.now())
        );
        Page<PostListResponse> page = new PageImpl<>(content, pageable, 10);

        given(postService.findAll(any(PostSearchCondition.class), any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/api/posts")
                        .param("page", "1")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("제목6"))
                .andExpect(jsonPath("$.totalElements").value(10))
                .andExpect(jsonPath("$.totalPages").value(2));

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(postService).findAll(any(PostSearchCondition.class), pageableCaptor.capture());
        Pageable captured = pageableCaptor.getValue();
        assertThat(captured.getPageNumber()).isEqualTo(1);
        assertThat(captured.getPageSize()).isEqualTo(5);
    }

}