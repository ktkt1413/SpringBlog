package com.sparta.postingboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDto {
    private String title;
    private String name;
    private String content;
    private String password;

    public PostRequestDto(String title, String writer, String password, String content) {
        this.title = title;
        this.name = writer;
        this.password = password;
        this.content = content;
    }
}