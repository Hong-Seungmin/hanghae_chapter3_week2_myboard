package com.sparta.myboard.config.security;

import com.sparta.myboard.exception.ResponseException;
import com.sparta.myboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        return userRepository.findOneByUsername(username)
                             .orElseThrow(() -> new ResponseException(HttpStatus.BAD_REQUEST, "가입되지 않은 계정입니다."));
    }
}
