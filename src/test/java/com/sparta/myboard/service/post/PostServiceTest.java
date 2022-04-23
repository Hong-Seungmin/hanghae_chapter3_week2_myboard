package com.sparta.myboard.service.post;

import com.sparta.myboard.repository.LikesRepository;
import com.sparta.myboard.repository.PostRepository;
import com.sparta.myboard.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private LikesRepository likesRepository;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void 포스트_저장() throws Exception {
        //given

        //when

        //then
    }

    @Test
    public void 포스트_목록_조회() throws Exception {
        //given

        //when

        //then
    }

    @Test
    public void 포스트_수정() throws Exception {
        //given

        //when

        //then
    }

    @Test
    public void 포스트_1건_조회() throws Exception {
        //given

        //when

        //then
    }

    @Test
    public void 포스트_삭제() throws Exception {
        //given

        //when

        //then
    }
}