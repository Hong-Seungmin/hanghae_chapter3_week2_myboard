package com.sparta.myboard.service.user;

import com.sparta.myboard.domain.user.User;
import com.sparta.myboard.domain.user.UserInfoResponseDto;
import com.sparta.myboard.domain.user.UserLoginRequestDto;
import com.sparta.myboard.domain.user.UserRegisterRequestDto;
import com.sparta.myboard.exception.ResponseException;
import com.sparta.myboard.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    @Mock
    private UserRepository userRepository;

    @Spy
    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @InjectMocks
    private UserService userService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void 회원등록() throws Exception {
        //given
        UserRegisterRequestDto requestDto = new UserRegisterRequestDto("testId", "password", "password", "nickname");
        User user = new User(requestDto.getUsername(), passwordEncoder.encode(requestDto.getPassword()),
                             requestDto.getNickname());


        //mocking
        given(userRepository.existsByUsername(any())).willReturn(false);
        given(userRepository.save(any(User.class))).willReturn(user);
        given(userRepository.findOneByUsername(any())).willReturn(Optional.of(user));

        //when

        userService.registerUser(requestDto.getUsername(), requestDto.getNickname(), requestDto.getPassword());

        //then
        User resultUser = userRepository.findOneByUsername(requestDto.getUsername()).get();

        assertEquals(requestDto.getUsername(), resultUser.getUsername());
        assertEquals(requestDto.getNickname(), resultUser.getNickname());
        assertTrue(passwordEncoder.matches(requestDto.getPassword(), resultUser.getPassword()));
    }

    @Test
    public void 중복된_아이디로_회원등록_시도() throws Exception {
        //given
        UserRegisterRequestDto requestDto = new UserRegisterRequestDto("testId", "password", "password", "nickname");
        User user = new User(requestDto.getUsername(), passwordEncoder.encode(requestDto.getPassword()),
                             requestDto.getNickname());


        //mocking
        given(userRepository.existsByUsername(any())).willReturn(true);

        //when/then
        assertThrows(ResponseException.class,
                     () -> userService.registerUser(requestDto.getUsername(), requestDto.getNickname(),
                                                    requestDto.getPassword()));
    }

    @Test
    public void 로그인() throws Exception {
        //given
        UserLoginRequestDto requestDto = new UserLoginRequestDto("username", "password");
        User user = new User(requestDto.getUsername(), passwordEncoder.encode(requestDto.getPassword()), "nickname");

        //mocking
        given(userRepository.findOneByUsername(any())).willReturn(Optional.of(user));

        //when
        try {
            userService.login(requestDto.getUsername(), requestDto.getPassword());
        } catch (Exception e) {
            fail();
        }
        //then
        finally {
            assertTrue(true);
        }
    }

    @Test
    public void 로그인_실패_없는_아이디() throws Exception {
        //given
        UserLoginRequestDto requestDto = new UserLoginRequestDto("username", "password");
        User user = new User("imid", passwordEncoder.encode(requestDto.getPassword()), "nickname");

        //mocking
        given(userRepository.findOneByUsername(any())).willThrow(ResponseException.class);

        //when
        try {
            userService.login(requestDto.getUsername(), requestDto.getPassword());

            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void 로그인_실패_비밀번호() throws Exception {
        //given
        UserLoginRequestDto requestDto = new UserLoginRequestDto("username", "password");
        User user = new User(requestDto.getUsername(), requestDto.getPassword(), "nickname");

        //mocking
        given(userRepository.findOneByUsername(any())).willReturn(Optional.of(user));

        //when
        try {
            userService.login(requestDto.getUsername(), requestDto.getPassword());

            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void 중복아이디_체크() throws Exception {
        //given
        String username = "username";

        //mocking
        given(userRepository.existsByUsername(any())).willReturn(false);

        //when
        try {
            userService.isDuplicationUsername(username);

        } catch (Exception e) {
            fail();
        }
        //then
        //===========================
        //given
        username = "username";

        //mocking
        given(userRepository.existsByUsername(any())).willReturn(true);

        //when
        try {
            userService.isDuplicationUsername(username);

            fail();
        } catch (Exception e) {

        }
        //then
    }

    @Test
    public void 회원정보_받기() throws Exception {
        //given
        String username = "username";
        User user = new User("username", null, null);

        given(userRepository.findOneByUsername(any())).willReturn(Optional.of(user));

        UserInfoResponseDto userInfo = null;
        //when
        try {
            userInfo = userService.getUserInfo(username);
        } catch (Exception e) {
            fail();
        }

        //then
        assertEquals(username, userInfo.getUsername());

    }

    @Test
    public void 회원정보_받기_없는_회원() throws Exception {
        //given
        String username = "username";
        User user = new User("hahahoho", null, null);

        given(userRepository.findOneByUsername(any())).willThrow(ResponseException.class);

        UserInfoResponseDto userInfo = null;
        //when
        try {
            userInfo = userService.getUserInfo(username);
            fail();
        } catch (Exception e) {

        }

        //then
        assertNull(userInfo);

    }
}