package com.sparta.postingboard.service;


import com.sparta.postingboard.dto.LoginRequestDto;
import com.sparta.postingboard.dto.SignupRequestDto;
import com.sparta.postingboard.dto.StatusCodeDto;
import com.sparta.postingboard.entity.User;
import com.sparta.postingboard.entity.UserRoleEnum;
import com.sparta.postingboard.jwt.JwtUtil;
import com.sparta.postingboard.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public StatusCodeDto signup(SignupRequestDto signupRequestDto){
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        //사용자 ROLE확인
        UserRoleEnum role = UserRoleEnum.USER;
        if(signupRequestDto.isAdmin()){
            if(!ADMIN_TOKEN.equals(signupRequestDto.getAdminToken())){
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능 합니다");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = new User(username, password, role);
        userRepository.save(user);

        // return
        StatusCodeDto responseDto = new StatusCodeDto(HttpStatus.CREATED.value(), "회원가입 성공");
        return responseDto;
    }

    public StatusCodeDto login(LoginRequestDto loginRequestDto, HttpServletResponse jwtResponse) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(() -> //Optional<T>에 orElseThrow 메서드는 결과값이 T로 나온다 (User)
                new IllegalArgumentException("등록된 사용자가 없습니다."));
        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호 불일치");
        }

        // Jwt 토큰 생성, response에 넣기
        String token = jwtUtil.createToken(user.getUsername(), user.getRole());
        // Jwt 쿠키 저장
        jwtUtil.addJwtToCookie(token, jwtResponse);

        StatusCodeDto responseDto = new StatusCodeDto(HttpStatus.OK.value(), "로그인 성공");

        return responseDto;
    }
}