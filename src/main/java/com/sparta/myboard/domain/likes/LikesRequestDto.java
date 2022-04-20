package com.sparta.myboard.domain.likes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikesRequestDto {

    @NotBlank(message = "postId를 넣어주세요.")
    private Long postId;
}
