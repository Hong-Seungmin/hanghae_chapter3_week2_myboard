package com.sparta.myboard.domain.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponseDto {

    private String username;
    private String nickname;
}
