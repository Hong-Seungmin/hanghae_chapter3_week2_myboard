package com.sparta.myboard.controller.user;

import com.sparta.myboard.BaseIntegrationTest;
import com.sparta.myboard.service.user.UserService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;

public class UserControllerTest extends BaseIntegrationTest {

    @Autowired
    private UserService userService;

    @Nested
    @DisplayName("회원가입")
    class register {

        @Test
        public void 성공() {
            
            //given
            

            //when


            //then
        }
        
        @Test
        public void 실패_아이디_규칙() {
            
            //given
            

            //when


            //then
            
        }

        @Test
        public void 실패_패스워드_규칙() {

            //given
            

            //when


            //then
            
        }

        @Test
        public void 실패_패스워드_확인_입력이_다름() {

            //given
            

            //when


            //then
            
        }

        @Test
        public void 실패_아이디_패스워드_길이규칙() throws Exception {
            //given

            //when

            //then
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