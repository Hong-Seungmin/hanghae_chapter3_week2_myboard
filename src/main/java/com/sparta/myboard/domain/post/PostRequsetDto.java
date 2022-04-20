package com.sparta.myboard.domain.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequsetDto {

    private String contents;
    private String imagePath;
}
