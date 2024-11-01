package com.example.notion.domain.workspace.service;

import com.example.notion.domain.user.entitiy.User;
import com.example.notion.domain.user.repository.UserRepository;
import com.example.notion.domain.workspace.dto.request.CreateWorkspaceRequest;
import com.example.notion.domain.workspace.dto.request.InviteMemberRequest;
import com.example.notion.domain.workspace.dto.request.UpdateWorkspaceRequest;
import com.example.notion.domain.workspace.dto.response.WorkspaceDetailResponse;
import com.example.notion.domain.workspace.entity.Workspace;
import com.example.notion.domain.workspace.entity.WorkspaceMember;
import com.example.notion.domain.workspace.entity.WorkspaceRole;
import com.example.notion.domain.workspace.repository.WorkspaceMemberRepository;
import com.example.notion.domain.workspace.repository.WorkspaceRepository;
import com.example.notion.global.util.SecurityUtil;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import org.springframework.security.access.AccessDeniedException;

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
    private UserRepository userRepository;

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

    @Test
    @DisplayName("권한 없는 사용자의 워크스페이스 수정 실패")
    void updateWorkspace_WithoutPermission_ThrowsException() {
        // given
        UpdateWorkspaceRequest request = new UpdateWorkspaceRequest("수정된 이름", "수정된 설명");
        WorkspaceMember memberWithoutPermission = WorkspaceMember.builder()
                .id(2L)
                .workspace(testWorkspace)
                .user(testUser)
                .role(WorkspaceRole.MEMBER)  // MEMBER 권한
                .build();

        when(securityUtil.getCurrentUser()).thenReturn(testUser);
        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(testWorkspace));
        when(workspaceMemberRepository.findByWorkspaceIdAndUserId(1L, 1L))
                .thenReturn(Optional.of(memberWithoutPermission));

        // when & then
        assertThrows(AccessDeniedException.class, () ->
                workspaceService.updateWorkspace(1L, request));
    }

    @Test
    @DisplayName("멤버 초대 성공")
    void inviteMember_Success() {
        // given
        User invitedUser = User.builder()
                .id(2L)
                .email("invited@test.com")
                .build();

        InviteMemberRequest request = new InviteMemberRequest("invited@test.com");
        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(testWorkspace));
        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(invitedUser));

        // when
        workspaceService.inviteMember(1L, request);

        // then
        verify(workspaceMemberRepository).save(any(WorkspaceMember.class));
    }

    @Test
    @DisplayName("워크스페이스 상세 조회 성공")
    void getWorkspaceDetail_Success() {
        // given
        when(securityUtil.getCurrentUser()).thenReturn(testUser);
        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(testWorkspace));
        // 현재 사용자가 멤버인지 확인하는 부분 추가
        when(workspaceMemberRepository.findByWorkspaceIdAndUserId(1L, testUser.getId()))
                .thenReturn(Optional.of(testMember));
        // 전체 멤버 목록 조회
        when(workspaceMemberRepository.findByWorkspaceId(1L))
                .thenReturn(List.of(testMember));

        // when
        WorkspaceDetailResponse response = workspaceService.getWorkspaceDetail(1L);

        // then
        assertEquals(testWorkspace.getName(), response.name());
        assertFalse(response.members().isEmpty());
    }

    @Test
    @DisplayName("워크스페이스 삭제 성공")
    void deleteWorkspace_Success() {
        // given
        when(securityUtil.getCurrentUser()).thenReturn(testUser);
        when(workspaceRepository.findById(1L)).thenReturn(Optional.of(testWorkspace));

        // when
        workspaceService.deleteWorkspace(1L);

        // then
        verify(workspaceRepository).delete(testWorkspace);
    }

}

