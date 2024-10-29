package com.example.notion.global.util;

import com.example.notion.domain.user.entitiy.User;
import com.example.notion.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtil {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        // SecurityContext에서 Authentication 객체를 가져옴
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("인증 정보가 없습니다.");
        }

        String email = authentication.getName(); //JWT 토큰의 subject로 저장된 email이 반환

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("사용자 정보가 없습니다."));
    }
}
