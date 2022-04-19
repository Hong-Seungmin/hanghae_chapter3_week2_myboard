package com.sparta.myboard.domain.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRegisterRequestDto {

    private String username;
    private String password;
    private String passwordCheck;
    private String nickname;
}
