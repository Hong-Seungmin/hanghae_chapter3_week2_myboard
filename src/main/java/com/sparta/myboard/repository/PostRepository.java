package com.sparta.myboard.repository;

import com.sparta.myboard.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAll(Pageable pageable);

    Post findOneByPostId(Long postId);

    Long deleteOneByPostId(Long postId);
}
