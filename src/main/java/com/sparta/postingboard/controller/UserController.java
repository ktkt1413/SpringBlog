package com.sparta.postingboard.controller;


import com.sparta.postingboard.dto.LoginRequestDto;
import com.sparta.postingboard.dto.SignupRequestDto;
import com.sparta.postingboard.dto.StatusCodeDto;
import com.sparta.postingboard.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;  // di(의존성) 주입

    @GetMapping("/login-page")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage(){
        return "signup";
    }

    @PostMapping("/signup")
    public StatusCodeDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto){
        return userService.signup(signupRequestDto);
    }
    @PostMapping("/login")
    public StatusCodeDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse JwtResponse){
        return userService.login(loginRequestDto, JwtResponse);
    }

}
