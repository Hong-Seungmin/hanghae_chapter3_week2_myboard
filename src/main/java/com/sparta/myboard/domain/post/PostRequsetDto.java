package com.sparta.myboard.domain.post;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostRequsetDto {

    private String contents;
    private String imagePath;
}
