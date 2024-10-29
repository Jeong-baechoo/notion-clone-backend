package com.example.notion.domain.workspace.dto.response;

import java.time.LocalDateTime;

public record WorkspaceResponse(

        String name,

        String description,

        String role,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}
