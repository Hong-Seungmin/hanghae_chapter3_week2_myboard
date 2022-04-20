package com.sparta.myboard.controller.likes;

import com.sparta.myboard.config.security.jwt.JwtTokenProvider;
import com.sparta.myboard.domain.common.ResponseMessage;
import com.sparta.myboard.domain.likes.LikesRequestDto;
import com.sparta.myboard.exception.ResponseException;
import com.sparta.myboard.service.likes.LikesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/post/like")
@RequiredArgsConstructor
@Slf4j
public class LikesController {

    private final LikesService likesService;

    @PostMapping("")
    public ResponseEntity<ResponseMessage> like(@RequestBody @Valid LikesRequestDto likesRequestDto,
            HttpServletRequest request) {

        //TODO 좋아요

        String msg = isValid(likesRequestDto, request) ? "좋아요 체크 성공" : "좋아요 해제 성공";

        return new ResponseEntity<>(new ResponseMessage(true, msg), HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<ResponseMessage> disLike(@RequestBody @Valid LikesRequestDto likesRequestDto,
            HttpServletRequest request) {

        //TODO 좋아요 취소

        String msg = isValid(likesRequestDto, request) ? "좋아요 체크 성공" : "좋아요 해제 성공";

        return new ResponseEntity<>(new ResponseMessage(true, msg), HttpStatus.OK);
    }

    private boolean isValid(@RequestBody LikesRequestDto likesRequestDto,
            HttpServletRequest request) {
        if (likesRequestDto.getPostId() == null) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.");
        }

        String username = JwtTokenProvider.getUsernameFromRequest(request);
        if (username == null) {
            throw new ResponseException(HttpStatus.UNAUTHORIZED, "로그인 후 이용해주세요.");
        }

        return likesService.updateLikes(username, likesRequestDto.getPostId());
    }
}
