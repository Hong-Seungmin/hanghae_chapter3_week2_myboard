package com.sparta.myboard.domain.post;

import com.sparta.myboard.domain.likes.Likes;
import com.sparta.myboard.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PostDto {

    private Long id;
    private String contents;
    private String imagePath;
    private User user;
    private List<Likes> likeList;
}
