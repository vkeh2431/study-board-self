package com.example.studyboardself.domain.post;

import com.example.studyboardself.domain.category.Category;
import com.example.studyboardself.domain.category.CategoryRepository;
import com.example.studyboardself.domain.like.PostLikeRepository;
import com.example.studyboardself.domain.member.Member;
import com.example.studyboardself.domain.member.MemberRepository;
import com.example.studyboardself.domain.member.Role;
import com.example.studyboardself.domain.tag.Tag;
import com.example.studyboardself.domain.tag.TagRepository;
import com.example.studyboardself.dto.post.*;
import com.example.studyboardself.global.exception.BusinessException;
import com.example.studyboardself.global.exception.ErrorCode;
import com.example.studyboardself.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final PostLikeRepository postLikeRepository;

    public PostResponse findById(Long id, Long memberId) {
        int updated = postRepository.incrementViewCount(id);
        if (updated == 0) {
            throw new ResourceNotFoundException("Post", id);
        }
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", id));
        long likeCount = postLikeRepository.countByPostId(id);
        boolean liked = postLikeRepository.existsByMemberIdAndPostId(memberId, id);
        return PostResponse.of(post, likeCount, liked);
    }

    public Page<PostListResponse> findAll(PostSearchCondition postSearchCondition, Pageable pageable) {
        return postRepository.search(postSearchCondition, pageable);
    }

    public PostResponse create(Long memberId, PostCreateRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.UNAUTHORIZED));
        Post post = Post.builder()
                .title(request.title())
                .content(request.content())
                .member(member)
                .build();
        applyCategory(post, request.categoryId());
        applyTags(post, request.tagNames());
        Post saved = postRepository.save(post);
        return PostResponse.of(saved, 0L, false);
    }

    public List<PostListResponse> findPopular() {
        return null;
    }

    public PostResponse update(Long id, Long memberId, Role role, PostUpdateRequest request) {
        return null;
    }

    public void delete(Long id, Long memberId, Role role) {
    }

    private void applyCategory(Post post, Long categoryId) {
        Category category = (categoryId == null) ? null
                : categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", categoryId));
        post.assignCategory(category);
    }

    private void applyTags(Post post, List<String> tagNames) {
        Set<String> tags = (tagNames == null) ? Set.of()
                : tagNames.stream()
                .filter(name -> name != null && !name.isBlank())
                .collect(Collectors.toSet());

        for (String name : tags) {
            Tag tag = tagRepository.findByName(name)
                    .orElseGet(() -> tagRepository.save(Tag.builder().name(name).build()));
            post.addTag(tag);
        }

    }
}
