package com.sparta.postingboard.entity;


//DB에 실제로 저장되는 데이터를 가짐

import com.sparta.postingboard.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "post")
@NoArgsConstructor

public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name="username", nullable = false)
    private String username;

    @Column(name="content", nullable = false,  length = 500)
    private String content;

    public Post(PostRequestDto requestDto, String username){
        this.title = requestDto.getTitle();
        this.username = username;
        this.content = requestDto.getContent();
    }

    public void update(PostRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }
}




//    public Post(PostRequestDto postRequestDto){
//        this.title = postRequestDto.getTitle();
//        this.name = postRequestDto.getName();
//        this.content = postRequestDto.getContent();
//        this.password = postRequestDto.getPassword();
//    }
//    //setter
//    public void setTitle(String title){
//        this.title = title;
//    }
//
//    public void setName(String name){
//        this.name = name;
//    }
//
//    public void setContent(String content){
//        this.content = content;
//    }
//
//    //비밀번호 체크하는 함수
//
//    public void checkPassword(String inputPassword){
//        if(!password.equals(inputPassword)){
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//        }
//    }
//
//}
