package com.sparta.postingboard.controller;

import com.sparta.postingboard.dto.PostRequestDto;
import com.sparta.postingboard.dto.PostResponseDto;
import com.sparta.postingboard.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController             // 외부에서 들어오는 요청을 수신하여 응답값을 주는 레이어페키지
@RequestMapping("/api")   // api라는 경로를 통한 요청이 들어왔을때 아래의 클래스 혹은 메서드를 사용하겠다

public class PostController {
    private final PostService postService;  // service객체는 final변수로 선언됨= 1번만 선언하고 계속 사용하겠다

    public PostController(PostService postService){
        this.postService = postService;
    }

    @GetMapping("/posts")     // post라는 호스트가 붙으면 아래의 메서드를 호출하겠다
    public List<PostResponseDto> getPostList() {
        return postService.getPostList();
    }

    @GetMapping("/posts/{id}")
    public PostResponseDto getPost(@PathVariable Long id){
        return postService.getPost(id);
    }

    @PostMapping("/posts")   //  전체수정 기능
    public void createPost(@RequestBody PostRequestDto postRequestDto) {  //RequestBody로 담아서 생성해줌
        postService.createPost(postRequestDto);
    }

    @PutMapping("/posts/{id}")
    public void updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto) {
        postService.updatePost(id, postRequestDto);
    }

  // 깃허브 커밋 완료 테스트
    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable Long id, @RequestBody PostRequestDto postRequest){
        postService.deletePost(id, postRequest);
    }
}
