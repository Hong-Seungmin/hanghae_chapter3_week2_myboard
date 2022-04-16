package com.sparta.myboard.domain.user.response;

import com.sparta.myboard.domain.user.UserInfoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseUserRegisterDto {

    private String message;
    private UserInfoDto userInfo;
}
