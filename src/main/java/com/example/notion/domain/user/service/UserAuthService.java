package com.example.notion.domain.user.service;

import com.example.notion.domain.user.dto.request.LoginRequest;
import com.example.notion.domain.user.dto.request.SignupRequest;
import com.example.notion.domain.user.dto.response.TokenResponse;

public interface UserAuthService {

    TokenResponse login(LoginRequest request);

    void signup(SignupRequest request);

    void logout(String token);

}
