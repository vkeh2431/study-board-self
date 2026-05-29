package com.example.studyboardself.domain;

import com.example.studyboardself.domain.post.PostController;
import com.example.studyboardself.domain.post.PostService;
import com.example.studyboardself.dto.post.PostCreateRequest;
//import com.example.studyboardself.dto.post.PostListResponse;
import com.example.studyboardself.dto.post.PostResponse;
//import com.example.studyboardself.dto.post.PostUpdateRequest;
//import com.example.studyboardself.global.exception.ResourceNotFoundException;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
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
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("게시글 생성")
    void create_post() throws Exception {
        PostCreateRequest request = new PostCreateRequest("제목", "내용", "작성자");
        PostResponse response = new PostResponse(1L, "제목", "내용", "작성자", 0, LocalDateTime.now(), LocalDateTime.now());

        given(postService.create(any(PostCreateRequest.class))).willReturn(response);

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("제목"))
                .andExpect(jsonPath("$.author").value("작성자"));
    }

    @Test
    @DisplayName("게시글 생성 - 제목이 비어있으면 400")
    void create_post_blank_title() throws Exception {
        PostCreateRequest request = new PostCreateRequest("", "내용", "작성자");

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("게시글 생성 - 내용이 비어있으면 400")
    void create_post_blank_content() throws Exception {
        PostCreateRequest request = new PostCreateRequest("제목", "", "작성자");

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("게시글 생성 - 작성자가 비어있으면 400")
    void create_post_blank_author() throws Exception {
        PostCreateRequest request = new PostCreateRequest("제목", "내용", "");

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("게시글 생성 - 제목이 200자를 초과하면 400")
    void create_post_title_too_long() throws Exception {
        PostCreateRequest request = new PostCreateRequest("가".repeat(201), "내용", "작성자");

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("게시글 생성 - 작성자가 50자를 초과하면 400")
    void create_post_author_too_long() throws Exception {
        PostCreateRequest request = new PostCreateRequest("제목", "내용", "가".repeat(51));

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
