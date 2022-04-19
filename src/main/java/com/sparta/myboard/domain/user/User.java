package com.sparta.myboard.domain.user;

import com.sparta.myboard.domain.likes.Likes;
import com.sparta.myboard.domain.likes.LikesDto;
import com.sparta.myboard.domain.post.Post;
import com.sparta.myboard.domain.post.PostDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Entity
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likeList = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER) // 왜 값타입으로 하는지 찾아봐야한다.
    private List<String> roles = new ArrayList<>();

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void addPost(PostDto postDto) {
        Post post = new Post(postDto);
        postList.add(post);
    }

    public void removePost(PostDto postDto) {
        Post post = new Post(postDto);
        postList.remove(post);
    }

    public void addLike(LikesDto likesDto) {
        Likes likes = new Likes(likesDto);
        likeList.add(likes);
    }

    public void removeLike(LikesDto likesDto) {
        Likes likes = new Likes(likesDto);
        likeList.remove(likes);

//        int i = likeList.indexOf(likes);
//        Likes targetLike = likeList.get(i);
//        targetLike.setUser(null);
//        likeList.remove(i);
    }

    public void addRole(String role) {
        if (roles.contains(role)) {
            return;
        }
        roles.add(role);
    }

    public void removeRole(String role) {
        roles.remove(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
