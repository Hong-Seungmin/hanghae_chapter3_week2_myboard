package com.sparta.myboard.domain.post;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponseDto {

    private Long postId;
    private Long likeCount;

    private String contents;
    private String imagePath;
    private String nickname;

    private boolean liked;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
