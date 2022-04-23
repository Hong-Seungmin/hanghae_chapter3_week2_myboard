package com.sparta.myboard.service.likes;

import com.sparta.myboard.domain.post.Post;
import com.sparta.myboard.domain.user.User;
import com.sparta.myboard.exception.ResponseException;
import com.sparta.myboard.repository.LikesRepository;
import com.sparta.myboard.repository.PostRepository;
import com.sparta.myboard.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class LikesServiceTest {

    @InjectMocks
    private LikesService likesService;

    @Mock
    private LikesRepository likesRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void 좋아요_활성화() throws Exception {
        //given
        String username = "username";
        Long postId = 100L;

        given(likesRepository.existsOneByUser_UsernameAndPost_Id(any(), any())).willReturn(false);
        given(userRepository.findOneByUsername(any())).willReturn(Optional.of(new User()));
        given(postRepository.findOneById(any())).willReturn(Optional.of(new Post()));

        //when
        boolean result = likesService.updateLikes(username, postId);

        //then
        Assertions.assertTrue(result);

    }

    @Test
    public void 좋아요_비활성화() throws Exception {
        //given
        String username = "username";
        Long postId = 100L;

        given(likesRepository.existsOneByUser_UsernameAndPost_Id(any(), any())).willReturn(true);
        given(userRepository.findOneByUsername(any())).willReturn(Optional.of(new User()));
        given(postRepository.findOneById(any())).willReturn(Optional.of(new Post()));

        //when
        boolean result = likesService.updateLikes(username, postId);

        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void 좋아요_사용자_인증_실패() throws Exception {
        //given
        String username = "username";
        Long postId = 100L;

        given(likesRepository.existsOneByUser_UsernameAndPost_Id(any(), any())).willReturn(false);
        given(userRepository.findOneByUsername(any())).willThrow(ResponseException.class);
        given(postRepository.findOneById(any())).willReturn(Optional.of(new Post()));

        //when
        try {
            likesService.updateLikes(username, postId);

            fail();
        } catch (Exception e) {
        }
        //then
    }

    @Test
    public void 좋아요_포스트_인증_실패() throws Exception {
        //given
        String username = "username";
        Long postId = 100L;

        given(likesRepository.existsOneByUser_UsernameAndPost_Id(any(), any())).willReturn(false);
        given(userRepository.findOneByUsername(any())).willReturn(Optional.of(new User()));
        given(postRepository.findOneById(any())).willThrow(ResponseException.class);

        //when

        try {
            likesService.updateLikes(username, postId);

            fail();
        } catch (Exception e) {
        }

        //then
    }
}
