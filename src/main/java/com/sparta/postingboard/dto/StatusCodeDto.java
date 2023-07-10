package com.sparta.postingboard.dto;


import lombok.Getter;

@Getter
public class StatusCodeDto {
    private String message;
    private int statusDto;

    public StatusCodeDto(int statusDto, String message){
        this.message = message;
        this.statusDto = statusDto;
    }
}
