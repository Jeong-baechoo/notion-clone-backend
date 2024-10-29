package com.example.notion.domain.workspace.repository;

import com.example.notion.domain.user.entitiy.User;
import com.example.notion.domain.workspace.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkspaceRepositroy extends JpaRepository<Workspace, Long> {
    Optional<Workspace> findByName(String name);

    // 소유자의 워크스페이스 목록 조회
    List<Workspace> findByOwner(User owner);

    // 특정 사용자가 멤버로 속한 워크스페이스 조회
    @Query("SELECT w FROM Workspace w JOIN w.members m WHERE m.user = :user")
    List<Workspace> findWorkspacesByMember(@Param("user") User user);
}
