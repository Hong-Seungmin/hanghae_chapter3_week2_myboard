package com.sparta.myboard.controller.post;

import com.sparta.myboard.config.security.jwt.JwtTokenProvider;
import com.sparta.myboard.controller.BaseIntegrationTest;
import com.sparta.myboard.domain.post.PostRequestDto;
import com.sparta.myboard.repository.PostRepository;
import com.sparta.myboard.service.post.PostService;
import com.sparta.myboard.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostControllerTest extends BaseIntegrationTest {

    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;

    @Nested
    @DisplayName("포스트 등록")
    class savePost {

        @Test
        public void 성공_인증_사용자() throws Exception {
            //given
            PostRequestDto requestDto = new PostRequestDto("contents", "layout", "imagePath");
            userService.registerUser("username", "nickname", "password");
            String token = JwtTokenProvider.generateToken("username");

            //when
            ResultActions resultActions = mvc.perform(post("/api/post")
                                                              .header(HttpHeaders.AUTHORIZATION, token)
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(objectMapper.writeValueAsString(requestDto))
                                                              .accept("*/*"));

            //then
            resultActions.andExpect(jsonPath("$.success").value(true));
        }

        @Test
        public void 실패_미인증_사용자() throws Exception {
            //given
            PostRequestDto requestDto = new PostRequestDto("contents", "layout", "imagePath");
//            userService.registerUser("username", "nickname", "password");
//            String token = JwtTokenProvider.generateToken("username");

            //when
            ResultActions resultActions = mvc.perform(post("/api/post")
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(objectMapper.writeValueAsString(requestDto))
                                                              .accept("*/*"));

            //then
            resultActions.andExpect(jsonPath("$.success").value(false));
        }

        @Test
        public void 실패_토큰오류() throws Exception {
            //given
            PostRequestDto requestDto = new PostRequestDto("contents", "layout", "imagePath");
            userService.registerUser("username", "nickname", "password");
            String token = JwtTokenProvider.generateToken("asdasd");

            //when
            ResultActions resultActions = mvc.perform(post("/api/post")
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .header(HttpHeaders.AUTHORIZATION, token)
                                                              .content(objectMapper.writeValueAsString(requestDto))
                                                              .accept("*/*"))
                                             .andDo(print());

            //then
            resultActions.andExpect(jsonPath("$.success").value(false));
        }
    }

    @Nested
    @DisplayName("포스트 조회")
    class getPost {

        @Test
        public void 성공_목록조회() throws Exception {
            //given
            userService.registerUser("username", "nickname", "password");
            int cnt = 5;
            for (int i = 0; i < cnt; i++) {
                postService.savePost(new PostRequestDto("Contents" + i, "Layout" + i, "imagePath" + i), "username");
            }

            String token = JwtTokenProvider.generateToken("username");

            //when
            ResultActions resultActions = mvc.perform(get("/api/post")
                                                              .header(HttpHeaders.AUTHORIZATION, token)
                                                              .accept("*/*"))
                                             .andDo(print());

            //then
            resultActions.andExpect(status().isOk());
            resultActions.andExpect(jsonPath("$.length()").value(cnt));
        }

        @Test
        public void 성공_1건조회() throws Exception {
            //given
            userService.registerUser("username", "nickname", "password");
            postService.savePost(new PostRequestDto("Contents", "Layout", "imagePath"), "username");


            //when
            ResultActions resultActions = mvc.perform(get("/api/post/1"))
                                             .andDo(print());

            //then
            resultActions.andExpect(status().isOk());
            resultActions.andExpect(jsonPath("$.contents").value("Contents"));
        }

        @Test
        public void 실패_없는_포스트_조회() throws Exception {
            //given
            userService.registerUser("username", "nickname", "password");
            postService.savePost(new PostRequestDto("Contents", "Layout", "imagePath"), "username");


            //when
            ResultActions resultActions = mvc.perform(get("/api/post/10"))
                                             .andDo(print());

            //then
            resultActions.andExpect(status().isBadRequest());
            resultActions.andExpect(jsonPath("$.success").value(false));
        }

    }

    @Nested
    @DisplayName("포스트 수정")
    class updatePost {

        @Test
        public void 성공_인증_사용자() throws Exception {
            //given
            userService.registerUser("username", "nickname", "password");
            postService.savePost(new PostRequestDto("Contents", "Layout", "imagePath"), "username");
            String token = JwtTokenProvider.generateToken("username");

            PostRequestDto requestDto = new PostRequestDto("update contents", "layout", "imagePath");

            //when
            ResultActions resultActions = mvc.perform(patch("/api/post/1")
                                                              .header(HttpHeaders.AUTHORIZATION, token)
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(objectMapper.writeValueAsString(requestDto))
                                                              .accept("*/*"))
                                             .andDo(print());

            //then
            resultActions.andExpect(jsonPath("$.success").value(true));

            resultActions = mvc.perform(get("/api/post/1"))
                               .andDo(print());

            resultActions.andExpect(jsonPath("$.contents").value("update contents"));
        }

        @Test
        public void 실패_미인증_사용자() throws Exception {
            //given
            userService.registerUser("hahahoho", "nickname", "password");
            postService.savePost(new PostRequestDto("Contents", "Layout", "imagePath"), "hahahoho");
            String token = JwtTokenProvider.generateToken("username");

            PostRequestDto requestDto = new PostRequestDto("update contents", "layout", "imagePath");

            //when
            ResultActions resultActions = mvc.perform(patch("/api/post/1")
                                                              .header(HttpHeaders.AUTHORIZATION, token)
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(objectMapper.writeValueAsString(requestDto))
                                                              .accept("*/*"))
                                             .andDo(print());

            //then
            resultActions.andExpect(jsonPath("$.success").value(false));

            //when 조회
            resultActions = mvc.perform(get("/api/post/1"))
                               .andDo(print());

            //then 조회
            resultActions.andExpect(jsonPath("$.contents").value("Contents"));
        }
    }

    @Nested
    @DisplayName("포스트 삭제")
    class removePost {

        @Test
        public void 성공_인증_사용자() throws Exception {
            //given
            userService.registerUser("username", "nickname", "password");
            postService.savePost(new PostRequestDto("Contents", "Layout", "imagePath"), "username");
            String token = JwtTokenProvider.generateToken("username");

            //when
            ResultActions resultActions = mvc.perform(delete("/api/post/1")
                                                              .header(HttpHeaders.AUTHORIZATION, token)
                                                              .accept("*/*"));

            //then
            resultActions.andExpect(jsonPath("$.success").value(true));

            //트랜잭션이 끝나야 delete문이 날아간다.
        }

        @Test
        public void 실패_미인증_사용자() throws Exception {
            //given
            userService.registerUser("username", "nickname", "password");
            postService.savePost(new PostRequestDto("Contents", "Layout", "imagePath"), "username");
            String token = JwtTokenProvider.generateToken("asdasd");

            //when
            ResultActions resultActions = mvc.perform(delete("/api/post/1")
                                                              .header(HttpHeaders.AUTHORIZATION, token)
                                                              .accept("*/*"));

            //then
            resultActions.andExpect(jsonPath("$.success").value(false));

            //트랜잭션이 끝나야 delete문이 날아간다.
        }
    }

}