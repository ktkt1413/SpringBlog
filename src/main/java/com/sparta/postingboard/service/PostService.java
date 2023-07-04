package com.sparta.postingboard.service;

//컨트롤러 뒤에서 실제 컨트롤러의 요청에 대한 서비스로직을 수행

import com.sparta.postingboard.dto.PostRequestDto;
import com.sparta.postingboard.dto.PostResponseDto;
import com.sparta.postingboard.entity.Post;
import com.sparta.postingboard.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service    // 실제 이 클래스는 bean으로 등록하기 위함 + 서비스클래스라는 것을 명시

public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    @Transactional
    public List<PostResponseDto> getPostList(){
        List<Post> postList = postRepository.findAllByOrderByCreateAtDesc();
        List<PostResponseDto> postResponseList = new ArrayList<>();
        for (Post post : postList){
            postResponseList.add(new PostResponseDto((post)));
        }
        return postResponseList;
    }

//    public List<PostResponseDto> getPostListV2() {
//        return postRepository.findAllByOrderByCreateAtDesc().stream()
//                .map(PostResponseDto:: new)
//                .toList(); // stram에서 List로 만들어줌
//    }

//    public PostResponseDto getPost(Long id){
//        Post post = findPost(id);
//        return new PostResponseDto(post);
//    }
    @Transactional
    public void createPost(PostRequestDto postRequestDto){

        Post post = new Post(postRequestDto.getTitle(), postRequestDto.getContent(), postRequestDto.getName(), postRequestDto.getPassword());
        postRepository.save(post);
    }
    @Transactional
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id없음"));
        return new PostResponseDto(post);
    }

    @Transactional   // 'save'를 사용하지 않아도 메서드 종료 전 결과값을 DB에 반영해줌
    public void updatePost(Long id, PostRequestDto postRequestDto) {

        Post postSaved = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id없음"));
        if (postSaved.isValidPassword(postRequestDto.getPassword())) {
            postSaved.update(postRequestDto.getTitle(), postRequestDto.getName(), postRequestDto.getName());
            postRepository.save(postSaved);
        } else {
            throw new IllegalArgumentException("패스워드 오류");
        }
    }

//        //비밀번호 체크
//        post.checkPassword(postRequestDto.getPassword());
//        // 필드 없데이트
//        post.setTitle(postRequestDto.getTitle());
//        post.setName(postRequestDto.getName());
//        post.setContent(postRequestDto.getContent());
//
//        return new PostResponseDto(post);
//    }


    public void deletePost(Long id, PostRequestDto postRequestDto){
        Post postDelete = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id없음"));
        String password = postRequestDto.getPassword();
        if (postDelete.isValidPassword(password)) {
            postRepository.delete(postDelete);
            System.out.println("삭제성공");
        }else {
            throw new IllegalArgumentException("패스워드 오류");
        }
    }

    private Post findPost(Long id){
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 회원이 존재하지 않습니다"));
    }

}
