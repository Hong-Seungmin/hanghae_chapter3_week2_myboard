package com.sparta.myboard.domain.likes;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LikesRequestDto {

    private Long postId;
}
