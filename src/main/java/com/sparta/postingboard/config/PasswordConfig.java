package com.sparta.postingboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {  // 빈으로 저장될때 passwordConfig로 저장됨

    @Bean
    public PasswordEncoder passwordEncoder(){   //bean으로 passwordEncoder 가 등록되면
        return new BCryptPasswordEncoder();  // BCryptPasswordEncoder 가 주입 됨 (BCrypt는 비밀번호를 암호화 해주는 hash함수)
    }
}


