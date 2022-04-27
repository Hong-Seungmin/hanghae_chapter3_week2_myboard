package com.sparta.myboard.repository;

import com.sparta.myboard.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAll(Pageable pageable);

    Optional<Post> findOneById(Long postId);

    void deleteOneById(Long postId);

    @Query(value = "select p from Post p left join fetch p.likeList l " +
            "group by p.id " +
            "order by count(l) desc ")
    List<Post> findAllSortedLike(Pageable pageable);
}
