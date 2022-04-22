package com.sparta.myboard.controller.exception;

import com.sparta.myboard.domain.common.ResponseMessage;
import com.sparta.myboard.exception.ResponseException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<Map<String, Object>> responseExceptionHandle(ResponseException e) {
        Map<String, Object> body = new HashMap<>();
        body.put("success", false);
        body.put("msg", e.getMessage());

        log.warn(e.getMessage());
        return new ResponseEntity<>(body, e.getHttpStatus());
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseMessage> otherExceptionHandle(Exception e) {
        log.warn(e.getMessage());
        return new ResponseEntity<>(new ResponseMessage(false, "잘못된 요청입니다."), HttpStatus.BAD_REQUEST);
    }
    /*   Exception.class 로 통일합시다!!
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseMessage> constraintViolationExceptionHandle(ConstraintViolationException e) {

        log.warn(e.getMessage());
        return new ResponseEntity<>(new ResponseMessage(false, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseMessage> methodArgumentNotNalidExceptionHandle(MethodArgumentNotValidException e) {
        log.warn(e.getMessage());
        return new ResponseEntity<>(
                new ResponseMessage(false, e.getBindingResult().getAllErrors().get(0).getDefaultMessage()),
                HttpStatus.BAD_REQUEST);
    }
     */



    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<ResponseMessage> noHandlerFoundExceptionHandle(NoHandlerFoundException e) {
        log.warn(e.getMessage());
        return new ResponseEntity<>(new ResponseMessage(false, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ResponseMessage> expiredJwtExceptionHandle(JwtException e) {

        log.warn(e.getMessage());
        return new ResponseEntity<>(new ResponseMessage(false, e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}
