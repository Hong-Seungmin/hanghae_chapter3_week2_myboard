package com.sparta.myboard.controller.user;

import com.sparta.myboard.controller.BaseIntegrationTest;
import com.sparta.myboard.domain.user.UserRegisterRequestDto;
import com.sparta.myboard.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private WebApplicationContext context;

    @Nested
    @DisplayName("회원가입")
    @WithAnonymousUser
    public class register {
        private String username = "username";
        private String password = "password";
        private String nickname = "nickname";

        @Test
        public void 성공() throws Exception {

            //given
            UserRegisterRequestDto requestDto = new UserRegisterRequestDto(username, password, password, nickname);

            //when
            ResultActions resultActions = mvc.perform(post("/api/user/register")
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(objectMapper.writeValueAsString(requestDto))
                                                              .accept("*/*"));


            //then
            resultActions.andExpect(status().isOk());

        }

        @Test
        public void 실패_아이디_규칙() throws Exception {

            //given
            UserRegisterRequestDto requestDto = new UserRegisterRequestDto("aa", password, password, nickname);

            //when
            ResultActions resultActions = mvc.perform(post("/api/user/register")
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(objectMapper.writeValueAsString(requestDto))
                                                              .accept("*/*"));


            //then
            resultActions.andExpect(status().isBadRequest())
                         .andExpect(jsonPath("$.msg").value("아이디는 최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)"));


        }

        @Test
        public void 실패_패스워드_길이규칙() throws Exception {
            //given
            UserRegisterRequestDto requestDto = new UserRegisterRequestDto(username, "asd", "asd", nickname);

            //when
            ResultActions resultActions = mvc.perform(post("/api/user/register")
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(objectMapper.writeValueAsString(requestDto))
                                                              .accept("*/*"));


            //then
            resultActions.andExpect(status().isBadRequest())
                         .andExpect(jsonPath("$.msg").value("비밀번호는 최소 4자 이상이며, 닉네임과 같은 값이 미포함"));


        }

        @Test
        public void 실패_패스워드_확인_입력이_다름() throws Exception {
            //given
            UserRegisterRequestDto requestDto = new UserRegisterRequestDto(username, "asad", "zxcc", nickname);

            //when
            ResultActions resultActions = mvc.perform(post("/api/user/register")
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(objectMapper.writeValueAsString(requestDto))
                                                              .accept("*/*"));


            //then
            resultActions.andExpect(status().isBadRequest())
                         .andExpect(jsonPath("$.msg").value("입력한 비밀번호가 서로 다릅니다."));


        }
    }

    @Nested
    @DisplayName("로그인")
    class login {

        @Test
        public void 성공() throws Exception {
            //given

            //when

            //then
        }

        @Test
        public void 성공_앞_뒤로_띄워쓰기_포함() throws Exception {
            //given

            //when

            //then
        }

        @Test
        public void 실패_로그인_인증_후_시도() throws Exception {
            //given

            //when

            //then
        }

        @Test
        public void 실패_비밀번호_틀림() throws Exception {
            //given

            //when

            //then
        }

        @Test
        public void 실패_아이디_없음() throws Exception {
            //given

            //when

            //then
        }

        @Test
        public void 실패_아이디_공란() throws Exception {
            //given

            //when

            //then
        }

        @Test
        public void 실패_비밀번호_공란() throws Exception {
            //given

            //when

            //then
        }

        @Test
        public void 실패_아이디_띄워쓰기_포함() throws Exception {
            //given

            //when

            //then
        }
    }

    @Nested
    @DisplayName("로그아웃")
    class logout {

        @Test
        public void 성공_로그아웃_클릭() throws Exception {
            //given

            //when

            //then
        }

        @Test
        public void 실패_토큰인증_오류() throws Exception {
            //given

            //when

            //then
        }
    }

    @Nested
    @DisplayName("회원정보 조회")
    class info {

        @Test
        public void 성공_인증_사용자_요청() throws Exception {
            //given

            //when

            //then
        }

        @Test
        public void 실패_미인증_사용자_요청() throws Exception {
            //given

            //when

            //then
        }
    }
}