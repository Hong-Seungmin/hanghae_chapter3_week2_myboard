package com.sparta.myboard.domain.user;


import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByIdAndPassword(String id, String password);
}
