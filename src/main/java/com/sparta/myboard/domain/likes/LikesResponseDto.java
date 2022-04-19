package com.sparta.myboard.domain.likes;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LikesResponseDto {

    private Long postId;
}
