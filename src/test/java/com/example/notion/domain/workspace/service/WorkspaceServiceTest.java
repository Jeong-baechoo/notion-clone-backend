package com.example.notion.domain.workspace.service;

import com.example.notion.domain.user.entitiy.User;
import com.example.notion.domain.workspace.dto.request.CreateWorkspaceRequest;
import com.example.notion.domain.workspace.dto.request.UpdateWorkspaceRequest;
import com.example.notion.domain.workspace.entity.Workspace;
import com.example.notion.domain.workspace.entity.WorkspaceMember;
import com.example.notion.domain.workspace.entity.WorkspaceRole;
import com.example.notion.domain.workspace.repository.WorkspaceMemberRepository;
import com.example.notion.domain.workspace.repository.WorkspaceRepository;
import com.example.notion.global.util.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkspaceServiceTest {

    @Mock
    private WorkspaceRepository workspaceRepository;

    @Mock
    private WorkspaceMemberRepository workspaceMemberRepository;

    @Mock
    private SecurityUtil securityUtil;

    @InjectMocks
    private WorkspaceServiceImpl workspaceService;

    private User testUser;
    private Workspace testWorkspace;
    private WorkspaceMember testMember;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)  // Mock 데이터 생성시 ID 필요
                .email("test@test.com")
                .name("테스트")
                .build();

        testWorkspace = Workspace.builder()
                .id(1L)  // Mock 데이터 생성시 ID 필요
                .name("테스트 워크스페이스")
                .description("설명")
                .owner(testUser)
                .build();

        testMember = WorkspaceMember.builder()
                .id(1L)  // Mock 데이터 생성시 ID 필요
                .workspace(testWorkspace)
                .user(testUser)
                .role(WorkspaceRole.OWNER)
                .build();
    }

    @Test
    @DisplayName("워크스페이스 생성 성공")
    void createWorkspace_Success() {
        // given
        CreateWorkspaceRequest request = new CreateWorkspaceRequest("새 워크스페이스", "설명");
        when(securityUtil.getCurrentUser()).thenReturn(testUser);
        when(workspaceRepository.save(any(Workspace.class))).thenReturn(testWorkspace);

        // when
        workspaceService.createWorkspace(request);

        // then
        verify(workspaceRepository).save(any(Workspace.class));
        verify(workspaceMemberRepository).save(any(WorkspaceMember.class));
    }

    @Test
    @DisplayName("워크스페이스 수정 성공")
    void updateWorkspace_Success() {
        // given
        UpdateWorkspaceRequest request = new UpdateWorkspaceRequest("수정된 이름", "수정된 설명");
        when(securityUtil.getCurrentUser()).thenReturn(testUser);
        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(testWorkspace));
        when(workspaceMemberRepository.findByWorkspaceIdAndUserId(1L, 1L))
                .thenReturn(Optional.of(testMember));

        // when
        workspaceService.updateWorkspace(1L, request);

        // then
        assertEquals("수정된 이름", testWorkspace.getName());
        assertEquals("수정된 설명", testWorkspace.getDescription());
    }

}

