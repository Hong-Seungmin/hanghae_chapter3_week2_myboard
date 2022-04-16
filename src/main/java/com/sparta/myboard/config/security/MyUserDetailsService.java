package com.sparta.myboard.config.security;

import com.sparta.myboard.domain.user.User;
import com.sparta.myboard.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("zzzzzzzzzzzzzzzzzzz");
        User user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));

        return new MyUserDetails(user);
    }

}
