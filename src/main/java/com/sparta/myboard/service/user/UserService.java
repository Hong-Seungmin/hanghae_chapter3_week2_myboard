package com.sparta.myboard.service.user;

import com.sparta.myboard.domain.user.User;
import com.sparta.myboard.domain.user.UserDto;
import com.sparta.myboard.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto register(UserDto userDto) {
        String password = userDto.getPassword();
        userDto.setPassword(passwordEncoder.encode(password));

        User user = userRepository.save(new User(userDto));
        return UserDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .build();
    }

}
