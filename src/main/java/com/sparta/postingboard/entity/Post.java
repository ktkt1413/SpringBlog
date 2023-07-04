package com.sparta.postingboard.entity;


//DB에 실제로 저장되는 데이터를 가짐

import com.sparta.postingboard.dto.PostRequestDto;
import com.sparta.postingboard.repository.PostRepository;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "post")
@NoArgsConstructor

public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String name;

    private String content;

    private String password;

    public Post(String title, String name, String content, String password) {
        this.title = title;
        this.name = name;
        this.content = content;
        this.password = password;
    }

    public void update(String title, String name, String content) {
        this.title = title;
        this.name = name;
        this.content = content;
    }

    public boolean isValidPassword(String inputPassword) {
        if (inputPassword.equals(this.password)) {
            return true;
        } else {
            return false;
        }
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
