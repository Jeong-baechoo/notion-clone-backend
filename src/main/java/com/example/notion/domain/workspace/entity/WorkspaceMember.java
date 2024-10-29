package com.example.notion.domain.workspace.entity;

import com.example.notion.domain.user.entitiy.User;
import com.example.notion.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "workspace_members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkspaceMember extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private WorkspaceRole role;  // OWNER, ADMIN, MEMBER 등


    @Builder
    public WorkspaceMember(Workspace workspace, User user, WorkspaceRole role) {
        this.workspace = workspace;
        this.user = user;
        this.role = role;
    }
}
