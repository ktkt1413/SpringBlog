package com.sparta.postingboard.service;

//컨트롤러 뒤에서 실제 컨트롤러의 요청에 대한 서비스로직을 수행

import com.sparta.postingboard.dto.PostRequestDto;
import com.sparta.postingboard.dto.PostResponseDto;
import com.sparta.postingboard.dto.StatusCodeDto;
import com.sparta.postingboard.entity.Post;
import com.sparta.postingboard.jwt.JwtUtil;
import com.sparta.postingboard.repository.PostRepository;
import io.jsonwebtoken.Claims;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service    // 실제 이 클래스는 bean으로 등록하기 위함 + 서비스클래스라는 것을 명시
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;

    //<게시글 작성하기>
    public PostResponseDto createPost(PostRequestDto requestDto, String token) {
        // 토큰으로 부터 유저이름 가져오기
        String username = getUsername(token);

        Post post = new Post(requestDto, username);
        // DB 저장 넘겨주기
        Post savePost = postRepository.save(post);

        // Entity -> ResponseDto
        PostResponseDto postResponseDto = new PostResponseDto(savePost);

        return postResponseDto;
    }

    //<전체 조회하기>
    public List<PostResponseDto> getPosts() {
        // db 조회 넘겨주기
        return postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponseDto::new).toList();
    }

    //<선택한 게시글 조회하기>
    public PostResponseDto getSelectedPost(Long id) {
        // 해당 메모가 DB에 존재하는지 확인
        Post post = findPost(id);
        // Entity -> ResponseDto
        PostResponseDto postResponseDto = new PostResponseDto(post);
        return postResponseDto;
    }

    //<수정하기>
    @Transactional   // 'save'를 사용하지 않아도 메서드 종료 전 결과값을 DB에 반영해줌
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, String token) {
        //해당 메모가 DB에 존재하는지 확인
        Post post = findPost(id);
        checkUsername(post, token);
        // Entity -> ResponseDto
        post.update(requestDto);

        PostResponseDto postResponseDto = new PostResponseDto(post);
        return postResponseDto;
    }

    public StatusCodeDto deletePost(Long id, String token){

        Post post = findPost(id);
        checkUsername(post,token);
        postRepository.delete(post);
        StatusCodeDto responseDto = new StatusCodeDto(HttpStatus.OK.value(), "삭제가 완료되었습니다");

        return responseDto;
    }

    private Post findPost(Long id){
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 회원이 존재하지 않습니다"));
    }

    private String getUsername(String token) {
        // 토큰에서 사용자 정보 가져오기
        Claims info = jwtUtil.getUserInfoFromToken(token);
        // 사용자 username
        String username = info.getSubject();

        return username;
    }

    private void checkUsername(Post post, String token) {
        String username = getUsername(token);

        if (!post.getUsername().equals(username)) {
            throw new IllegalArgumentException("작성자가 아닙니다.");
        }
    }

}
