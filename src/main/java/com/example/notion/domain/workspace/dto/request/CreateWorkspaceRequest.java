package com.example.notion.domain.workspace.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateWorkspaceRequest(
        @NotBlank(message = "워크스페이스 이름은 필수입니다")
        @Size(min = 2, max = 50, message = "워크스페이스 이름은 2-50자 사이여야 합니다")
        String name,

        @Size(max = 255, message = "설명은 255자를 초과할 수 없습니다")
        String description
) {}
