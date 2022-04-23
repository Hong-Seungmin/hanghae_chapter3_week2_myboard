package com.sparta.myboard.controller.likes;

import com.sparta.myboard.config.security.jwt.JwtTokenProvider;
import com.sparta.myboard.controller.BaseIntegrationTest;
import com.sparta.myboard.domain.likes.LikesRequestDto;
import com.sparta.myboard.domain.post.PostRequestDto;
import com.sparta.myboard.repository.LikesRepository;
import com.sparta.myboard.service.likes.LikesService;
import com.sparta.myboard.service.post.PostService;
import com.sparta.myboard.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


class LikesControllerTest extends BaseIntegrationTest {

    @Autowired
    private LikesService likesService;
    @Autowired
    private LikesRepository likesRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @Nested
    @DisplayName("좋아요")
    class like {

        @Test
        public void 성공_좋아요() throws Exception {
            //given
            userService.registerUser("username", "nickname", "password");
            postService.savePost(new PostRequestDto("Contents", "Layout", "imagePath"), "username");

            LikesRequestDto requestDto = new LikesRequestDto(1L);
            String token = JwtTokenProvider.generateToken("username");

            //when
            ResultActions resultActions = mvc.perform(post("/api/post/like")
                                                              .header(HttpHeaders.AUTHORIZATION, token)
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(objectMapper.writeValueAsString(requestDto))
                                                              .accept("*/*"));

            //then
            resultActions.andExpect(jsonPath("$.success").value(true));
        }

        @Test
        public void 실패_좋아요() throws Exception {
            //given
            userService.registerUser("username", "nickname", "password");
            postService.savePost(new PostRequestDto("Contents", "Layout", "imagePath"), "username");

            LikesRequestDto requestDto = new LikesRequestDto(1L);
            String token = JwtTokenProvider.generateToken("asd");

            //when
            ResultActions resultActions = mvc.perform(post("/api/post/like")
                                                              .header(HttpHeaders.AUTHORIZATION, token)
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(objectMapper.writeValueAsString(requestDto))
                                                              .accept("*/*"));

            //then
            resultActions.andExpect(jsonPath("$.success").value(false));
        }
    }

}