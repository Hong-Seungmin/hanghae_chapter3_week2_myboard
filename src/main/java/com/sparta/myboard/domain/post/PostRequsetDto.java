package com.sparta.myboard.domain.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequsetDto {

    @NotBlank(message = "내용을 적어주세요.")
    private String contents;

    private String layout;

    private String imagePath;
}
