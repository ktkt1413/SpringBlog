package com.sparta.postingboard.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


@Getter
public class PostRequestDto {
    @NotBlank  //null 과 "" 과 " " 모두 허용하지 않음
    private String title;
    @NotBlank
    private String content;
}