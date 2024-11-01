package com.example.notion.domain.workspace.service;

import com.example.notion.domain.workspace.dto.request.CreateWorkspaceRequest;
import com.example.notion.domain.workspace.dto.request.InviteMemberRequest;
import com.example.notion.domain.workspace.dto.request.UpdateWorkspaceRequest;
import com.example.notion.domain.workspace.dto.response.WorkspaceDetailResponse;
import com.example.notion.domain.workspace.dto.response.WorkspaceResponse;

import java.util.List;

public interface WorkspaceService {
    void createWorkspace(CreateWorkspaceRequest request);

    List<WorkspaceResponse> getWorkspace();

    void inviteMember(Long workspaceId, InviteMemberRequest request);

    void deleteWorkspace(Long workspaceId);

    void updateWorkspace(Long workspaceId, UpdateWorkspaceRequest request);

    void deleteMember(Long workspaceId, Long memberId);

    WorkspaceDetailResponse getWorkspaceDetail(Long workspaceId);
}
