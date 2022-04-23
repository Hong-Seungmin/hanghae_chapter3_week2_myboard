package com.sparta.myboard.domain.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDto {

    private Long postId;
    private Long likeCount;

    private String contents;
    private String layout;
    private String imagePath;
    private String nickname;

    private boolean liked;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;


    public PostResponseDto(Post post, boolean liked) {
        this.postId = post.getId();
        this.imagePath = post.getImagePath();
        this.contents = post.getContents();
        this.layout = post.getLayout();
        this.nickname = post.getUser().getNickname();
        this.likeCount = (long) (post.getLikeList() == null ? 0 : post.getLikeList().size());
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.liked = liked;
    }
}
