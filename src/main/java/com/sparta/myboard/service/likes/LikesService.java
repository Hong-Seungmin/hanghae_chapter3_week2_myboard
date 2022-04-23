package com.sparta.myboard.service.likes;

import com.sparta.myboard.domain.likes.Likes;
import com.sparta.myboard.domain.post.Post;
import com.sparta.myboard.domain.user.User;
import com.sparta.myboard.exception.ResponseException;
import com.sparta.myboard.repository.LikesRepository;
import com.sparta.myboard.repository.PostRepository;
import com.sparta.myboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public boolean updateLikes(String username, Long postId) {

        User user = getUser(username);
        Post post = getPost(postId);

        if (likesRepository.existsOneByUser_UsernameAndPost_Id(username, postId)) {

            likesRepository.deleteOneByUser_UsernameAndPost_Id(username, postId);

            return false;
        } else {

            Likes likes = new Likes(user, post);
            likesRepository.save(likes);
            user.addLike(likes);
            post.addLike(likes);

            return true;
        }
    }

    private User getUser(String username) {
        //TODO 디비에서 가져올것이 아니라 인증체에서 뽑아낸 후 확인해야한다.
        return userRepository.findOneByUsername(username)
                             .orElseThrow(() -> new ResponseException(HttpStatus.BAD_REQUEST, "인증되지 않은 아이디로 시도하였습니다."));

    }

    private Post getPost(Long postId) {
        return postRepository.findOneById(postId)
                             .orElseThrow(() -> new ResponseException(HttpStatus.BAD_REQUEST, "존재하지 않는 포스트 입니다."));
    }
}
