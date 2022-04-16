package com.sparta.myboard.domain.user.request;

import lombok.Getter;

@Getter
public class RequestUserRegisterDto {

    private String id;
    private String nickname;
    private String password;
    private String passwordConfirm;
}
