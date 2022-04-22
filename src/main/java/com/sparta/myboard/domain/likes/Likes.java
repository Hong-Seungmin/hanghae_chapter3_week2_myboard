package com.sparta.myboard.domain.likes;

import com.sparta.myboard.domain.post.Post;
import com.sparta.myboard.domain.user.User;
import com.sparta.myboard.util.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Likes extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LikesId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PostId")
    private Post post;

    /**
     * 새로운 좋아요 생성용
     */
    public Likes(User user, Post post) {
        this.user = user;
        this.post = post;
    }

    /**
     * 기존 좋아요 처리용
     */
    public Likes(LikesDto likesDto) {
        this.id = likesDto.getId();
        this.user = likesDto.getUser();
        this.post = likesDto.getPost();
    }

    public void setUser(User user) {
        this.user = user;
    }
}
