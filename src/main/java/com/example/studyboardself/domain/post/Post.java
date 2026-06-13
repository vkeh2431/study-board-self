package com.example.studyboardself.domain.post;

import com.example.studyboardself.common.BaseTimeEntity;
import com.example.studyboardself.domain.category.Category;
import com.example.studyboardself.domain.comment.Comment;
import com.example.studyboardself.domain.member.Member;
import com.example.studyboardself.domain.tag.PostTag;
import com.example.studyboardself.domain.tag.Tag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private int viewCount;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<PostTag> postTags = new ArrayList<>();

    private LocalDateTime deletedAt;

    @Builder
    public Post(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.viewCount = 0;
    }

    public List<String> getTagNames() {
        return postTags.stream()
                .map(postTag -> postTag.getTag().getName())
                .toList();
    }

    public void assignCategory(Category category) {
        this.category = category;
    }

    public void addTag(Tag tag) {
        this.postTags.add(PostTag.builder().post(this).tag(tag).build());
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public boolean isOwner(Long memberId) {
        return memberId != null && member != null
                && member.getId() != null && member.getId().equals(memberId);
    }

}
