package com.example.notion.domain.workspace.service;

import com.example.notion.domain.user.entitiy.User;
import com.example.notion.domain.user.repository.UserRepository;
import com.example.notion.domain.workspace.dto.request.CreateWorkspaceRequest;
import com.example.notion.domain.workspace.dto.request.InviteMemberRequest;
import com.example.notion.domain.workspace.dto.response.WorkspaceResponse;
import com.example.notion.domain.workspace.entity.Workspace;
import com.example.notion.domain.workspace.entity.WorkspaceMember;
import com.example.notion.domain.workspace.entity.WorkspaceRole;
import com.example.notion.domain.workspace.repository.WorkspaceMemberRepository;
import com.example.notion.domain.workspace.repository.WorkspaceRepositroy;
import com.example.notion.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceRepositroy workspaceRepository;
    private final UserRepository userRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final SecurityUtil securityUtil;

    @Override
    public void createWorkspace(CreateWorkspaceRequest request) {
        User currentUser = securityUtil.getCurrentUser();

        Workspace workspace = Workspace.builder()
                .name(request.name())
                .description(request.description())
                .owner(currentUser)
                .build();

        workspaceRepository.save(workspace);

        WorkspaceMember member = WorkspaceMember.builder()
                .workspace(workspace)
                .user(currentUser)
                .role(WorkspaceRole.OWNER)
                .build();

        workspaceMemberRepository.save(member);
    }

    @Override
    public List<WorkspaceResponse> getWorkspace() {
        User currentUser = securityUtil.getCurrentUser();
        List<Workspace> workspacesByMember = workspaceRepository.findWorkspacesByMember(currentUser);
        return workspacesByMember.stream()
                .map(workspace -> new WorkspaceResponse(
                        workspace.getName(),
                        workspace.getDescription(),
                        getMemberRole(workspace.getId(), currentUser.getId()),
                        workspace.getCreatedAt(),
                        workspace.getUpdatedAt()
                )).collect(Collectors.toList());
    }

    private String getMemberRole(Long workspaceId, Long userId) {
        WorkspaceMember member = workspaceMemberRepository.findByWorkspaceIdAndUserId(workspaceId, userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("워크스페이스 ID: %d에서 사용자 ID: %d의 멤버 정보를 찾을 수 없습니다.",
                                workspaceId, userId)
                ));
        return member.getRole().name(); // 역할 이름 반환
    }


    @Override
    public void inviteMember(Long workspaceId, InviteMemberRequest request) {
        // 해당 워크스페이스 멤버를 초대
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new IllegalArgumentException("워크스페이스를 찾을 수 없습니다."));

        User invitedUser = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("초대할 사용자를 찾을 수 없습니다."));

        if (workspaceMemberRepository.existsByWorkspaceAndUser(workspace, invitedUser)) {
            throw new IllegalArgumentException("이미 워크스페이스의 멤버입니다.");
        }

        WorkspaceMember member = WorkspaceMember.builder()
                .workspace(workspace)
                .user(invitedUser)
                .role(WorkspaceRole.MEMBER)
                .build();

        workspaceMemberRepository.save(member);

    }

    @Override
    public void deleteWorkspace(Long workspaceId) {
        User currentUser = securityUtil.getCurrentUser();
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new IllegalArgumentException("워크스페이스를 찾을 수 없습니다."));

        // 소유자 확인
        if (!workspace.getOwner().equals(currentUser)) {
            throw new AccessDeniedException("워크스페이스를 삭제할 권한이 없습니다.");
        }

        workspaceRepository.delete(workspace);
    }
}