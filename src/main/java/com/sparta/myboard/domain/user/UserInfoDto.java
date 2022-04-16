package com.sparta.myboard.domain.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserInfoDto {
    private String id;
    private String nickname;
}
