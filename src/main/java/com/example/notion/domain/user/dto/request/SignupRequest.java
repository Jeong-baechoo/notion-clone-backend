package com.example.notion.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignupRequest(
        @NotBlank(message = "이름은 필수입니다")
        String name,

        @NotBlank(message = "이메일은 필수입니다")
        @Email(message = "이메일 형식이 올바르지 않습니다")
        String email,

        @NotBlank(message = "비밀번호는 필수입니다")
        @Pattern(
                regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,20}$",
                message = "비밀번호는 영문, 숫자를 포함해 8~20자리여야 합니다"
        )
        String password
) {}
