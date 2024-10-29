package com.example.notion.domain.user.service;

import com.example.notion.domain.user.dto.request.LoginRequest;
import com.example.notion.domain.user.dto.request.SignupRequest;
import com.example.notion.domain.user.dto.response.TokenResponse;
import com.example.notion.domain.user.entitiy.User;
import com.example.notion.domain.user.repository.UserRepository;
import com.example.notion.global.jwt.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;    // 비밀번호 암호화
    private final JwtTokenProvider jwtTokenProvider;  // JWT 토큰 생성

    @Override
    public TokenResponse login(LoginRequest request) {

        // 1. 사용자 찾기
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(IllegalArgumentException::new);

        // 2. 비밀번호 확인
        if(!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다");
        }
        // 3. 토큰 생성
        String token = jwtTokenProvider.createToken(user.getEmail()
                , Collections.singletonList("ROLE_USER"));
        return new TokenResponse(token, user.getEmail(), user.getPassword());
    }

    @Override
    public void signup(SignupRequest request) {
        //1. 이메일 중복 확인
        if(userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
        User user = User.builder()
                .email(request.email())
                .password(request.password())
                .name(request.name())
                .build();

        userRepository.save(user);
    }

    @Override
    public void logout(String token) {
        // 토큰 만료
        jwtTokenProvider.expireToken(token);
    }
}
