package com.sparta.myboard.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRegisterRequestDto {

    private String username;
    private String password;
    private String passwordCheck;
    private String nickname;
}
