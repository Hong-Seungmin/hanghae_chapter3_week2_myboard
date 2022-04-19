package com.sparta.myboard.repository;

import com.sparta.myboard.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findOneByUsername(String username);

    boolean existsByUsername(String username);
}
