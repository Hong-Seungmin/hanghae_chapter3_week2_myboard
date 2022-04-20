package com.sparta.myboard.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRegisterRequestDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "[a-zA-Z0-9]{3,20}", message = "최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 4, max = 20, message = "최소 4자 이상이며, 닉네임과 같은 값이 미포함")
    private String password;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String passwordCheck;
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;
}
