package com.example.notion.domain.workspace.repository;

import com.example.notion.domain.user.entitiy.User;
import com.example.notion.domain.workspace.entity.Workspace;
import com.example.notion.domain.workspace.entity.WorkspaceMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember, Long> {
    List<WorkspaceMember> findByWorkspaceId(Long workspaceId);
    Optional<WorkspaceMember> findByWorkspaceIdAndUserId(Long workspaceId, Long userId);
    void deleteByWorkspaceIdAndUserId(Long workspaceId, Long userId);

    boolean existsByWorkspaceAndUser(Workspace workspace, User invitedUser);
}
