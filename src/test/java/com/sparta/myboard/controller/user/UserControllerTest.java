package com.sparta.myboard.controller.user;

import com.sparta.myboard.config.security.jwt.JwtTokenProvider;
import com.sparta.myboard.controller.BaseIntegrationTest;
import com.sparta.myboard.domain.user.UserLoginRequestDto;
import com.sparta.myboard.domain.user.UserRegisterRequestDto;
import com.sparta.myboard.repository.UserRepository;
import com.sparta.myboard.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
    private UserRepository userRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private WebApplicationContext context;


    private String username = "username";
    private String password = "password";
    private String nickname = "nickname";


    @Nested
    @DisplayName("회원가입")
    @WithAnonymousUser
    public class register {

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
            UserLoginRequestDto requestDto = new UserLoginRequestDto(username, password);


            userService.registerUser(username, nickname, password);

            //when
            ResultActions resultActions = mvc.perform(post("/api/user/login")
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(objectMapper.writeValueAsString(requestDto))
                                                              .accept("*/*"));


            //then
            resultActions.andExpect(status().isOk())
                         .andExpect(jsonPath("$.success").value(true));

        }

        @Test
        public void 실패_로그인_인증_후_시도() throws Exception {
            //given
            UserLoginRequestDto requestDto = new UserLoginRequestDto(username, password);

            String token = JwtTokenProvider.generateToken(username);
            userService.registerUser(username, nickname, password);

            //when
            ResultActions resultActions = mvc.perform(post("/api/user/login")
                                                              .header(HttpHeaders.AUTHORIZATION, token)
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(objectMapper.writeValueAsString(requestDto))
                                                              .accept("*/*"));


            //then
            resultActions.andExpect(status().isBadRequest())
                         .andExpect(jsonPath("$.msg").value("이미 로그인 중.."));

        }

        @Test
        public void 실패_비밀번호_틀림() throws Exception {
            //given
            UserLoginRequestDto requestDto = new UserLoginRequestDto(username, password);

            userService.registerUser(username, nickname, "sisisi");

            //when
            ResultActions resultActions = mvc.perform(post("/api/user/login")
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(objectMapper.writeValueAsString(requestDto))
                                                              .accept("*/*"));


            //then
            resultActions.andExpect(status().isBadRequest())
                         .andExpect(jsonPath("$.msg").value("로그인할 수 없는 비밀번호입니다."));

        }

        @Test
        public void 실패_아이디_없음() throws Exception {

        }
    }


    //TODO 이건 한번 조사해봐야함 로그아웃 기능자체가 없음
//    @Nested
//    @DisplayName("로그아웃")
//    class logout {
//
//        @Test
//        public void 성공_로그아웃_클릭() throws Exception {
//
//            //given
//            String token = JwtTokenProvider.generateToken(username);
//
//            userService.registerUser(username, nickname, password);
//            //when
//            ResultActions resultActions = mvc.perform(post("/api/user/logout")
//                                                              .header(HttpHeaders.AUTHORIZATION, token)
//                                                              .accept("*/*"));
//
//
//            //then
//            resultActions.andExpect(status().isOk())
//                         .andExpect(jsonPath("$.msg").value("로그아웃 성공"));
//            assertFalse(SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
//
//        }
//
//        @Test
//        public void 실패_토큰인증_오류() throws Exception {
//            //given
////            String token = JwtTokenProvider.generateToken(username);
//            String token = "asdasdasdd";
//
//            //when
//            ResultActions resultActions = mvc.perform(post("/api/user/logout")
//                                                              .header(HttpHeaders.AUTHORIZATION, token)
//                                                              .accept("*/*"));
//
//
//            //then
//            resultActions.andExpect(status().isBadRequest())
//                         .andExpect(jsonPath("$.msg").value("이미 로그인 중.."));
//
//        }
//    }
//
//    @Nested
//    @DisplayName("회원정보 조회")
//    class info {
//
//        @Test
//        public void 성공_인증_사용자_요청() throws Exception {
//            //given
//
//            //when
//
//            //then
//        }
//
//        @Test
//        public void 실패_미인증_사용자_요청() throws Exception {
//            //given
//
//            //when
//
//            //then
//        }
//    }
}