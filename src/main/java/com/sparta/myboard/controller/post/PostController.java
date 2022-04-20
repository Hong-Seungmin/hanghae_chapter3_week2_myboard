package com.sparta.myboard.controller.post;

import com.sparta.myboard.config.security.jwt.JwtTokenProvider;
import com.sparta.myboard.domain.common.ResponseMessage;
import com.sparta.myboard.domain.post.PostRequsetDto;
import com.sparta.myboard.domain.post.PostResponseDto;
import com.sparta.myboard.exception.ResponseException;
import com.sparta.myboard.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/post/")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @GetMapping("/")
    public ResponseEntity<List<PostResponseDto>> getAllPost(
            @RequestParam(value = "page", required = false, defaultValue = "0") Long page,
            HttpServletRequest request) {

        //TODO 모든포스트 조회 (페이징)

        String username = getUsernameFromRequest(request);
        List<PostResponseDto> allPost = postService.getAllPost(page, username);

        return new ResponseEntity<>(allPost, HttpStatus.OK);

    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getOnePost(@PathVariable Long postId, HttpServletRequest request) {

        //TODO 포스트 상세보기 ( 1개 조회 )

        String username = getUsernameFromRequest(request);
        PostResponseDto onePost = postService.getOnePost(postId, username);

        return new ResponseEntity<>(onePost, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ResponseMessage> savePost(@RequestBody @Valid PostRequsetDto postRequsetDto,
            HttpServletRequest request) {

        //TODO 포스트 등록

        String username = getUsernameFromRequest(request);
        if (username == null) {
            throw new ResponseException(HttpStatus.UNAUTHORIZED, "로그인 후 이용해주세요.");
        }

        postService.savePost(postRequsetDto, username);
        return new ResponseEntity<>(new ResponseMessage(true, "포스트 기록 성공"), HttpStatus.OK);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<ResponseMessage> updatePost(@RequestBody @Valid PostRequsetDto postRequsetDto,
            HttpServletRequest request, @PathVariable Long postId) {

        //TODO 포스트 수정

        String username = getUsernameFromRequest(request);

        postService.updatePost(postRequsetDto, postId, username);

        return new ResponseEntity<>(new ResponseMessage(true, "포스트 수정 성공"), HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ResponseMessage> deletePost(HttpServletRequest request, @PathVariable Long postId) {

        //TODO 포스트 삭제

        String username = getUsernameFromRequest(request);

        postService.deletePost(postId, username);

        return new ResponseEntity<>(new ResponseMessage(true, "포스트 삭제 성공"), HttpStatus.OK);
    }

    /**
     * 토큰이 정상적으로 있다면 username, 없다면 null 반환
     */
    private String getUsernameFromRequest(HttpServletRequest request) {
        String jwt = JwtTokenProvider.getJwtFromRequest(request);
        if (jwt == null) {
            return null;
        }
        return JwtTokenProvider.getUsernameFromJWT(jwt);
    }
}
