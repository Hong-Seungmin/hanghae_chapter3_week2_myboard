package com.sparta.myboard.domain.likes;

import com.sparta.myboard.domain.post.Post;
import com.sparta.myboard.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikesDto {

    private Long id;
    private User user;
    private Post post;
}
