package com.sparta.myboard.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String message;

    public ResponseException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
