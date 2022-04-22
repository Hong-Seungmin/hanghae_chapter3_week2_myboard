package com.sparta.myboard.service.user;

import com.sparta.myboard.domain.user.User;
import com.sparta.myboard.domain.user.UserInfoResponseDto;
import com.sparta.myboard.exception.ResponseException;
import com.sparta.myboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(String username, String nickname, String password) {

        isDuplicationUsername(username);

        User newUser = new User(username, passwordEncoder.encode(password), nickname);
        newUser.addRole("ROLE_USER");
        userRepository.save(newUser);
    }

    @Transactional
    public void login(String username, String password) {

        User user = userRepository.findOneByUsername(username)
                                  .orElseThrow(() ->
                                                       new ResponseException(
                                                               HttpStatus.BAD_REQUEST,
                                                               "로그인할 수 없는 아이디입니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "로그인할 수 없는 비밀번호입니다.");
        }

        user.updateLoginTime();
    }

    public void isDuplicationUsername(String username) {

        if (userRepository.existsByUsername(username) || username == null || username.isEmpty()) {
            throw new ResponseException(HttpStatus.CONFLICT, "가입할 수 없는 아이디입니다.");
        }
    }

    public UserInfoResponseDto getUserInfo(String username){
        User user = userRepository.findOneByUsername(username)
                                  .orElseThrow(()-> new ResponseException(HttpStatus.BAD_REQUEST, "사용자 정보를 찾을 수 없습니다."));

        return new UserInfoResponseDto(user.getUsername(), user.getNickname());
    }
}
