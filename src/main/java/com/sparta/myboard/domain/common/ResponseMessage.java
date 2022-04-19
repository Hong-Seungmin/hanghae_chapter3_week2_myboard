package com.sparta.myboard.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseMessage {
    private boolean success; // isSuccess가 어울리지만, API문서에 success로 정하였음.
    private String msg;
}
