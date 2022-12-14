package com.project.cloneproject.controller.response;

import com.project.cloneproject.domain.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostResponseDto {
    private Long postId;
    private MemberSummaryDto member;
    private String imageUrl;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private final Long likeNum = 0L;
    private final Long commentsNum = 0L;

    public PostResponseDto(Post post) {
        this.postId = post.getId();
        this.member = new MemberSummaryDto(post.getMember());
        this.content = post.getContent();
        this.imageUrl = post.getImageUrl();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}
