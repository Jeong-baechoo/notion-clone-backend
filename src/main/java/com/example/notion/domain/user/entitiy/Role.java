package com.example.notion.domain.user.entitiy;

import lombok.Getter;

@Getter
public enum Role {
    //User Role: 노션 시스템 전체 권한 (관리자/일반 사용자)
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String key;

    Role(String key) {
        this.key = key;
    }
}
