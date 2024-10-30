package com.example.notion.domain.workspace.dto.response;

public record MemberResponse(
        Long id,
        String email,
        String name,
        String role
) {}
