package com.sparta.myboard.controller.user;

import com.sparta.myboard.config.security.jwt.JwtTokenProvider;
import com.sparta.myboard.domain.common.ResponseMessage;
import com.sparta.myboard.domain.user.UserInfoResponseDto;
import com.sparta.myboard.domain.user.UserLoginRequestDto;
import com.sparta.myboard.domain.user.UserRegisterRequestDto;
import com.sparta.myboard.exception.ResponseException;
import com.sparta.myboard.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseMessage> register(@RequestBody @Valid UserRegisterRequestDto userRegisterRequestDto,
            HttpServletRequest request) {

        //TODO 회원가입

        String jwt = JwtTokenProvider.getJwtFromRequest(request);
        if (JwtTokenProvider.validateToken(jwt)) {
            return new ResponseEntity<>(new ResponseMessage(false, "이미 로그인 중.."), HttpStatus.BAD_REQUEST);
        }

        String username = userRegisterRequestDto.getUsername();
        String nickname = userRegisterRequestDto.getNickname();
        String password = userRegisterRequestDto.getPassword();
        String passwordCheck = userRegisterRequestDto.getPasswordCheck();

        if (!password.equals(passwordCheck)) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "입력한 비밀번호가 서로 다릅니다.");
        } else if (password.contains(nickname)) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "비밀번호에 닉네임을 포함할 수 없습니다.");
        }

        userService.registerUser(username, nickname, password);


        return new ResponseEntity<>(new ResponseMessage(true, "회원가입 성공"), HttpStatus.OK);
    }

    @GetMapping("/register/{username}")
    public ResponseEntity<ResponseMessage> isDuplicate(@PathVariable String username) {

        //TODO 중복 아이디 검색
        userService.isDuplicationUsername(username.trim());

        return new ResponseEntity<>(new ResponseMessage(true, "가능한 아이디"), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseMessage> login(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto,
            HttpServletResponse response, HttpServletRequest request) {

        //TODO 로그인

        String jwt = JwtTokenProvider.getJwtFromRequest(request);
        if (JwtTokenProvider.validateToken(jwt)) {
            return new ResponseEntity<>(new ResponseMessage(false, "이미 로그인 중.."), HttpStatus.BAD_REQUEST);
        }

        String username = userLoginRequestDto.getUsername();
        String password = userLoginRequestDto.getPassword();
        userService.login(username, password);

        jwt = JwtTokenProvider.generateToken(username);
        response.setHeader(JwtTokenProvider.JWT_HEADER_KEY_NAME, jwt);

        return new ResponseEntity<>(new ResponseMessage(true, "로그인 성공"), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseMessage> logout(HttpServletRequest request) {

        //TODO 로그아웃 , 일단 보류
        String jwt = JwtTokenProvider.getUsernameFromJWT(request.getHeader(JwtTokenProvider.JWT_HEADER_KEY_NAME));

        SecurityContextHolder.clearContext();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // jwt를 이용한 로그아웃은 토근을 따로 저장해두었다가 삭제하는 식으로 구현해야한다.
        // 저장소에 토큰이있다면 로그인 중, 토큰이 없다면 로그아웃 상태라고 보면된다.

        return new ResponseEntity<>(new ResponseMessage(true, "로그아웃 성공"), HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<UserInfoResponseDto> info(@RequestParam(value = "username", required = false) String username,
            @AuthenticationPrincipal UserDetails user) {

        //TODO 회원 정보 조회
        if (user == null) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "로그인 후 이용해주세요.");
        }
        String username1 = user.getUsername();

        UserInfoResponseDto userInfoResponseDto = userService.getUserInfo(username1);

        return new ResponseEntity<>(userInfoResponseDto, HttpStatus.OK);
    }
}
