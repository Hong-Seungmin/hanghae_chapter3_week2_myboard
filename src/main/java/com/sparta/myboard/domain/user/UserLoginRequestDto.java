package com.sparta.myboard.domain.user;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class UserLoginRequestDto {

    private String username;
    private String password;
}
