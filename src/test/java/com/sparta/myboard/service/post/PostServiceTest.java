package com.sparta.myboard.service.post;

import com.sparta.myboard.domain.post.Post;
import com.sparta.myboard.domain.post.PostRequestDto;
import com.sparta.myboard.domain.post.PostResponseDto;
import com.sparta.myboard.domain.user.User;
import com.sparta.myboard.exception.ResponseException;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

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
        PostRequestDto postRequestDto = new PostRequestDto("hahahoho", "layout", "imagePath");
        String username = "username";
        User user = new User();

        given(userRepository.findOneByUsername(any())).willReturn(Optional.of(user));

        //when
        postService.savePost(postRequestDto, username);

        //then
        assertEquals(user.getPostList().get(0).getContents(), "hahahoho");

    }

    //TODO 실제디비 테스트를 해야할 것 같다.
//    @Test
//    public void 포스트_목록_조회() throws Exception {
//        //given
//        //TODO Page<Post> Mock 데이터를 수작업으로 만들어야하는가!?... 일단 패스..
////        Page<Post> posts = null;
////
////        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
////
////        given(postRepository.findAll(any(Pageable.class))).willReturn(posts);
//
//        //TODO Page<Post> 대신 List<Post>로 진행..하려했지만, 아마 타입에러가 날것같다. ㅋㅋ;;;;;;;;
//        List<Post> posts = new ArrayList<>();
//        posts.add(new Post("1", "1"));
//        posts.add(new Post("2", "2"));
//        posts.add(new Post("3", "3"));
//        posts.add(new Post("4", "4"));
//        posts.add(new Post("5", "5"));
//        posts.add(new Post("6", "6"));
//        posts.add(new Post("7", "7"));
//        posts.add(new Post("8", "8"));
//        posts.add(new Post("9", "9"));
//        posts.add(new Post("10", "10"));
//        posts.add(new Post("11", "11"));
//
//        given(postRepository.findAll()).willReturn(posts);
//
//
////        사용자가 좋아요 누른것도.. Mock객체 만들기가 어렵다.. 실제 디비에서 테스트해야할 요소같다.
////        given(likesRepository.existsOneByUser_UsernameAndPost_Id(any(),any(Long.class))).willReturn()
//        //when
//        postService.getAllPost(0, null);
//
//        //then
//    }

    @Test
    public void 포스트_수정() throws Exception {
        //given
        PostRequestDto postRequestDto = new PostRequestDto("contents", "layout", "imagePath");
        Long postId = 1L;
        String username = "username";
        String writerUsername = "username";

        Post post = new Post("zzzz", "ggggg");
        post.updateLayout("nanun layout");
        post.setUser(new User(writerUsername, null, null));

        given(postRepository.findOneById(any(Long.class))).willReturn(Optional.of(post));

        //when
        try {
            postService.updatePost(postRequestDto, postId, username);
        } catch (Exception e) {
            fail();
        }

        //then
        assertEquals(postRequestDto.getContents(), post.getContents());
        assertEquals(postRequestDto.getImagePath(), post.getImagePath());
        assertEquals(postRequestDto.getLayout(), post.getLayout());
    }

    @Test
    public void 포스트_수정_실패_다른사람_아이디_사칭() throws Exception {
        //given
        PostRequestDto postRequestDto = new PostRequestDto("contents", "layout", "imagePath");
        Long postId = 1L;
        String username = "username";
        String writerUsername = "realUser";

        Post post = new Post("zzzz", "ggggg");
        post.updateLayout("nanun layout");
        post.setUser(new User(writerUsername, null, null));

        given(postRepository.findOneById(any(Long.class))).willReturn(Optional.of(post));

        //when
        try {
            postService.updatePost(postRequestDto, postId, username);

            fail();
        } catch (Exception e) {

        }

        //then
        assertNotEquals(postRequestDto.getContents(), post.getContents());
        assertNotEquals(postRequestDto.getImagePath(), post.getImagePath());
        assertNotEquals(postRequestDto.getLayout(), post.getLayout());
    }

    @Test
    public void 포스트_1건_조회() throws Exception {
        //TODO 이번 코드는 좀 이상하다. 근데... 검증 방법이 안떠오른다.
        //given
        Long postId = 0L;
        String username = null;
        Post post = new Post("contents", "imagePath");
        post.setUser(new User("writerName", null, "null"));

        given(postRepository.findOneById(any())).willReturn(Optional.of(post));
        //when
        PostResponseDto responseDto = postService.getOnePost(postId, username);

        //then
        assertEquals(post.getContents(), responseDto.getContents());
        assertEquals(post.getImagePath(), responseDto.getImagePath());
    }

    @Test
    public void 포스트_삭제() throws Exception {
        //given
        Long postId = 0L;
        String username = "username";
        String writerName = "username";

        Post post = new Post("contents", "imagePath");
        post.setUser(new User(writerName, null, "null"));

        given(postRepository.findOneById(any())).willReturn(Optional.of(post));
//        given(postRepository.deleteOneById(any())).willThrow(ResponseException.class); // 리턴값 상이하다는 오류가 뜬다. 뭔가 다른 방법을 써야하는듯?


        //when
        try {
            postService.deletePost(postId, username);
        } catch (Exception e) {
            //then
            fail();
        }
    }

    @Test
    public void 포스트_삭제_실패_없는_포스트() throws Exception {
        //given
        Long postId = 0L;
        String username = "username";
        String writerName = "username";

        Post post = new Post("contents", "imagePath");
        post.setUser(new User(writerName, null, null));

        given(postRepository.findOneById(any())).willThrow(ResponseException.class);
//        given(postRepository.deleteOneById(any())).willThrow(ResponseException.class); // 리턴값 상이하다는 오류가 뜬다. 뭔가 다른 방법을 써야하는듯?


        //when
        try {
            postService.deletePost(postId, username);
            fail();
        } catch (Exception e) {
            //then
        }
    }

    @Test
    public void 포스트_삭제_실패_다른_작성자() throws Exception {
        //given
        Long postId = 0L;
        String username = "username";
        String writerName = "namari";

        Post post = new Post("contents", "imagePath");
        post.setUser(new User(writerName, null, null));

        given(postRepository.findOneById(any())).willReturn(Optional.of(post));
//        given(postRepository.deleteOneById(any())).willThrow(ResponseException.class); // 리턴값 상이하다는 오류가 뜬다. 뭔가 다른 방법을 써야하는듯?


        //when
        try {
            postService.deletePost(postId, username);
            fail();
        } catch (Exception e) {
            //then
        }
    }
}