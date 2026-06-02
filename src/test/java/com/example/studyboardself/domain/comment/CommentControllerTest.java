package com.example.studyboardself.domain.comment;


import com.example.studyboardself.dto.comment.CommentCreateRequest;
import com.example.studyboardself.dto.comment.CommentResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("댓글 생성")
    void create_comment() throws Exception {
        CommentCreateRequest request = new CommentCreateRequest("댓글 내용", "작성자");
        CommentResponse response = new CommentResponse(1L, 1L, "댓글 내용", "작성자", LocalDateTime.now(), LocalDateTime.now());

        given(commentService.create(eq(1L), any(CommentCreateRequest.class))).willReturn(response);

        mockMvc.perform(post("/api/posts/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content").value("댓글 내용"))
                .andExpect(jsonPath("$.author").value("작성자"));
    }

    @Test
    @DisplayName("댓글 생성 시 게시글이 없으면 404")
    void create_comment_post_not_found() throws Exception {

    }

    @Test
    @DisplayName("댓글 생성 시 내용이 비어있으면 400 에러")
    void create_comment_content_blank_validation_fail() throws Exception {

    }

    @Test
    @DisplayName("댓글 생성 시 작성자가 비어있으면 400 에러")
    void create_comment_author_blank_validation_fail() throws Exception {

    }

    @Test
    @DisplayName("게시글의 댓글 목록 조회")
    void findByPostId_comments() throws Exception {

    }

    @Test
    @DisplayName("댓글 목록 조회 시 게시글이 없으면 404")
    void findByPostId_post_not_found() throws Exception {

    }

    @Test
    @DisplayName("댓글 수정")
    void update_comment() throws Exception {

    }

    @Test
    @DisplayName("댓글 수정 시 댓글이 없으면 404")
    void update_comment_not_found() throws Exception {

    }

    @Test
    @DisplayName("댓글 수정 시 내용이 비어있으면 400 에러")
    void update_commnet_content_blank_validation_fail() throws Exception {

    }

    @Test
    @DisplayName("댓글 삭제")
    void delete_comment() throws Exception {

    }
}
