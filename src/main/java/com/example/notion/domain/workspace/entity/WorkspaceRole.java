package com.example.notion.domain.workspace.entity;

import lombok.Getter;

public enum WorkspaceRole {
    OWNER("ROLE_WORKSPACE_OWNER", "워크스페이스 소유자"),
    ADMIN("ROLE_WORKSPACE_ADMIN", "관리자"),
    MEMBER("ROLE_WORKSPACE_MEMBER", "멤버");

    private final String key;
    private final String title;  // 표시용 이름 추가

    WorkspaceRole(String key, String title) {
        this.key = key;
        this.title = title;
    }
}
