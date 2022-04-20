package com.sparta.myboard.domain.post;

import com.sparta.myboard.domain.likes.Likes;
import com.sparta.myboard.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {

    private Long id;
    private String contents;
    private String imagePath;
    private User user;
    private List<Likes> likeList;
}
