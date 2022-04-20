package com.sparta.myboard.service.post;

import com.sparta.myboard.domain.post.Post;
import com.sparta.myboard.domain.post.PostDto;
import com.sparta.myboard.domain.post.PostRequsetDto;
import com.sparta.myboard.domain.post.PostResponseDto;
import com.sparta.myboard.domain.user.User;
import com.sparta.myboard.exception.ResponseException;
import com.sparta.myboard.repository.LikesRepository;
import com.sparta.myboard.repository.PostRepository;
import com.sparta.myboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikesRepository likesRepository;

    public List<PostResponseDto> getAllPost(int page, String username) {

        Pageable pageRequest = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        Page<Post> posts = postRepository.findAll(pageRequest);

        List<PostResponseDto> responseDtos = new ArrayList<>();
        posts.forEach((post) -> {
            boolean liked = false;
            if (username != null) {
                liked = likesRepository.existsOneByUser_UsernameAndPost_Id(username, post.getId());
            }
            PostResponseDto responseDto = new PostResponseDto(post, liked);
            responseDtos.add(responseDto);
        });

        return responseDtos;
    }

    public PostResponseDto getOnePost(Long postId, String username) {
        Post post = postRepository.findOneById(postId).orElseThrow(
                () -> new ResponseException(HttpStatus.BAD_REQUEST, "해당 포스트가 삭제되었습니다.")
        );
        boolean liked = false;
        if (username != null) {
            liked = likesRepository.existsOneByUser_UsernameAndPost_Id(username, postId);
        }

        return new PostResponseDto(post, liked);

    }

    @Transactional
    public void savePost(PostRequsetDto postRequsetDto, String username) {

        User user = userRepository.findOneByUsername(username)
                                  .orElseThrow(() ->
                                                       new ResponseException(HttpStatus.UNAUTHORIZED,
                                                                             "등록되지 않은 아이디입니다."));

        PostDto postDto = PostDto.builder()
                                 .contents(postRequsetDto.getContents())
                                 .imagePath(postRequsetDto.getImagePath())
                                 .build();
        Post post = user.addPost(postDto);
        postRepository.save(post);
    }

    @Transactional
    public void updatePost(PostRequsetDto postRequsetDto, Long postId, String username) {

        Post post = postRepository.findOneById(postId)
                                  .orElseThrow(
                                          () -> new ResponseException(HttpStatus.UNAUTHORIZED, "접근할 수 없는 포스트입니다."));

        String writerUsername = post.getUser().getUsername();
        if (writerUsername.equals(username)) {
            post.updateContents(postRequsetDto.getContents());
            post.updateImagePath(postRequsetDto.getImagePath());
        }else{
            throw new ResponseException(HttpStatus.UNAUTHORIZED, "편집할 수 없는 포스트입니다.");
        }
    }

    @Transactional
    public void deletePost(Long postId, String username) {
        Post post = postRepository.findOneById(postId)
                                  .orElseThrow(
                                          () -> new ResponseException(HttpStatus.UNAUTHORIZED, "접근할 수 없는 포스트입니다."));

        String writerUsername = post.getUser().getUsername();
        if (writerUsername.equals(username)) {
            postRepository.deleteOneById(postId);
        }else{
            throw new ResponseException(HttpStatus.UNAUTHORIZED, "삭제할 수 없는 포스트입니다.");
        }
    }
}
