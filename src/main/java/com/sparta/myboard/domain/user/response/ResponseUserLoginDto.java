package com.sparta.myboard.domain.user.response;

import com.sparta.myboard.domain.user.UserInfoDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseUserLoginDto {

    private String message;
    private UserInfoDto userInfo;
}
