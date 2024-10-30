package com.example.notion.domain.workspace.dto.response;

import java.util.List;

public record WorkspaceDetailResponse(
        String name,
        String description,
        List<MemberResponse> members
) {}
