package com.sparta.myboard.controller.post;

import com.sparta.myboard.BaseIntegrationTest;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import static org.junit.Assert.*;

public class PostControllerTest extends BaseIntegrationTest {

    @Nested
    @DisplayName("포스트 등록")
    class savePost {

        @Test
        public void 성공_인증_사용자() throws Exception {
            //given

            //when

            //then
        }

        @Test
        public void 실패_미인증_사용자() throws Exception {
            //given

            //when

            //then
        }

        @Test
        public void 실패_토큰오류() throws Exception {
            //given

            //when

            //then
        }
    }

    @Nested
    @DisplayName("포스트 조회")
    class getPost {

        @Test
        public void 성공_목록조회() throws Exception {
            //given

            //when

            //then
        }

        @Test
        public void 성공_1건조회() throws Exception {
            //given

            //when

            //then
        }

        @Test
        public void 실패_없는_포스트_조회() throws Exception {
            //given

            //when

            //then
        }

    }

    @Nested
    @DisplayName("포스트 수정")
    class updatePost {

        @Test
        public void 성공_인증_사용자() throws Exception {
            //given

            //when

            //then
        }

        @Test
        public void 실패_미인증_사용자() throws Exception {
            //given

            //when

            //then
        }
    }

    @Nested
    @DisplayName("포스트 삭제")
    class removePost {

        @Test
        public void 성공_인증_사용자() throws Exception {
            //given

            //when

            //then
        }

        @Test
        public void 실패_미인증_사용자() throws Exception {
            //given

            //when

            //then
        }
    }

}