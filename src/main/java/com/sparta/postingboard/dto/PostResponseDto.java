package com.sparta.postingboard.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.postingboard.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) // non_null인 것만 응답객체로(json형태로)넘겨주겠다
public class PostResponseDto {
    private Boolean success;
    private Long id;
    private String title;
    private String name;
    private String content;
    private LocalDateTime createdAT;
    private LocalDateTime modifiedAt;

    public PostResponseDto(Post post){
        this.success = true;
        this.id = post.getId();
        this.name = post.getName();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAT = post.getCreateAt();
        this.modifiedAt = post.getModifiedAt();
    }
    public PostResponseDto(Boolean success) {
        this.success = success;
    }
}
