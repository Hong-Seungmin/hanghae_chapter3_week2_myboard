package com.sparta.myboard.domain.post;

import com.sparta.myboard.domain.likes.Likes;
import com.sparta.myboard.domain.user.User;
import com.sparta.myboard.util.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PostId")
    private Long id;

    @Column(nullable = false)
    private String contents;

    @Column
    private String imagePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 50)  // https://joont92.github.io/jpa/JPA-%EC%84%B1%EB%8A%A5-%EC%B5%9C%EC%A0%81%ED%99%94/
    private List<Likes> likeList = new ArrayList<>();

    /**
     * 새로운 포스트 생성용
     */
    public Post(String contents, String imagePath) {
        this.contents = contents;
        this.imagePath = imagePath;
    }

    /**
     * 기존 포스트 처리용
     */
    public Post(PostDto postDto) {
        this.id = postDto.getId();
        this.contents = postDto.getContents();
        this.imagePath = postDto.getImagePath();
        this.user = postDto.getUser();
        this.likeList = postDto.getLikeList();
    }

    public void updateContents(String contents){
        this.contents = contents;
    }

    public void updateImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void addLike(Likes likes) {
        likeList.add(likes);
    }

    public void removeLike(Likes likes) {
        likeList.remove(likes);
    }

    public void setUser(User user) {
        this.user = user;
    }
}
