package com.sparta.myboard.repository;

import com.sparta.myboard.domain.likes.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    List<Likes> findAllByUser_UsernameAndPost(String username);

    Optional<Likes> findOneByUser_UsernameAndPost_PostId(String username, Long postId);

    boolean existsByUser_UsernameAndPost_PostId(String username, Long postId);
}
