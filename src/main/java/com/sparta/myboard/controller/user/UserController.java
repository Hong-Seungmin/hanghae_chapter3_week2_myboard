package com.sparta.myboard.controller.user;

import com.sparta.myboard.domain.user.UserDto;
import com.sparta.myboard.domain.user.UserInfoDto;
import com.sparta.myboard.domain.user.request.RequestUserRegisterDto;
import com.sparta.myboard.domain.user.response.ResponseUserRegisterDto;
import com.sparta.myboard.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseUserRegisterDto register(@RequestBody RequestUserRegisterDto registerDto) {
        UserDto userDto = userService.register(UserDto.builder()
                .id(registerDto.getId())
                .password(registerDto.getPassword())
                .nickname(registerDto.getNickname())
                .build());

        return ResponseUserRegisterDto.builder()
                .message("회원가입. 성공적.")
                .userInfo(UserInfoDto.builder()
                        .id(userDto.getId())
                        .nickname(userDto.getNickname())
                        .build())
                .build();
    }


    //    @PostMapping("/register")
    //    public UserResponseDto register(@RequestBody UserRegisterFormDto userRegisterFormDto) {
    //        return userService.registerUser(userRegisterFormDto);
    //    }
    //
    //    @GetMapping("/register/{userId}")
    //    public UserResponseDto vaildId(@PathVariable String userId) {
    //        int result = userService.isDuplicatedUserId(userId) ? 406 : 200;
    //        return new UserResponseDto(result, null);
    //    }
    //
    //    @PostMapping("/logind")
    //    public UserResponseDto login(@RequestBody UserLoginFormDto userLoginFormDto) {
    //        System.out.println("zzzzzzzzzzzzzzzzz");
    //        return null;
    //    }
}
