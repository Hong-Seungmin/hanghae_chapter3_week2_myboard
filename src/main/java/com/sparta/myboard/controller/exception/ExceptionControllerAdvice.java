package com.sparta.myboard.controller.exception;

import com.sparta.myboard.exception.ResponseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<Map<String, Object>> handler(ResponseException e) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", e.getMessage());

        return new ResponseEntity<>(body, e.getHttpStatus());
    }
}
