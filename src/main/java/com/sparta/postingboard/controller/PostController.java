package com.sparta.postingboard.controller;

import com.sparta.postingboard.dto.PostRequestDto;
import com.sparta.postingboard.dto.PostResponseDto;
import com.sparta.postingboard.dto.StatusCodeDto;
import com.sparta.postingboard.jwt.JwtUtil;
import com.sparta.postingboard.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j   // 사용자가 배포시 원하는 로깅 프레임워크 결정 및 사용하게 해줌, 추상화를 제공해주는 라이브러리(lombok과 비슷)
@RestController             // 외부에서 들어오는 요청을 수신하여 응답값을 주는 레이어페키지
@RequestMapping("/api")   // api라는 경로를 통한 요청이 들어왔을때 아래의 클래스 혹은 메서드를 사용하겠다
@RequiredArgsConstructor

public class PostController {

    private final PostService postService;  // service객체는 final변수로 선언됨= 1번만 선언하고 계속 사용하겠다
    private final JwtUtil jwtUtil;

    //<게시글작성>
    @PostMapping()
    public PostResponseDto createBoard(@RequestBody @Valid PostRequestDto requestDto,
                                        HttpServletRequest req) {

        String token = authentication(req);

        return postService.createPost(requestDto, token);
    }

    //<전체조회>
    @GetMapping("/posts")
    public List<PostResponseDto> getPosts() {
        return postService.getPosts();
    }

    //<선택조회>
    @GetMapping("/posts/{id}")
    public PostResponseDto getSelectedPost(@PathVariable Long id){
        return postService.getSelectedPost(id);
    }

    //<선택한 게시글 수정>

    @PutMapping("/posts/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody @Valid PostRequestDto requestDto,
                                    HttpServletRequest req) {
        String token = authentication(req);
        return postService.updatePost(id, requestDto, token);
    }

  // <게시글 삭제>
    @DeleteMapping("/posts/{id}")
    public StatusCodeDto deletePost(@PathVariable Long id, HttpServletRequest req){
        String token = authentication(req);
        return postService.deletePost(id, token);

    }

    private String authentication(HttpServletRequest req) {
        // 토큰값 가져오기
        String tokenValue = jwtUtil.getTokenFromRequest(req);
        // JWT 토큰 substring
        String token = jwtUtil.substringToken(tokenValue);

        // 토큰 검증
        if(!jwtUtil.validateToken(token)){
            throw new IllegalArgumentException("Token Error");
        }

        return token;
    }
}
