package com.sparta.myboard.domain.likes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikesRequestDto {

    @NotNull(message = "postId를 넣어주세요.")
    private Long postId;
}
