package com.sparta.myboard.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private String id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    public User(UserDto userDto) {
        this.id = userDto.getId();
        this.nickname = userDto.getNickname();
        this.password = userDto.getPassword();
    }

}
