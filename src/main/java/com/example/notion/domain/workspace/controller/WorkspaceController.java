package com.example.notion.domain.workspace.controller;

import com.example.notion.domain.workspace.dto.request.CreateWorkspaceRequest;
import com.example.notion.domain.workspace.dto.request.InviteMemberRequest;
import com.example.notion.domain.workspace.dto.request.UpdateWorkspaceRequest;
import com.example.notion.domain.workspace.dto.response.WorkspaceDetailResponse;
import com.example.notion.domain.workspace.dto.response.WorkspaceResponse;
import com.example.notion.domain.workspace.service.WorkspaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workspaces")  // @RequestMapping 사용
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    // 워크스페이스 생성
    @PostMapping("/create")
    public ResponseEntity<Void> createWorkspace(@RequestBody @Valid CreateWorkspaceRequest request) {
        workspaceService.createWorkspace(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 워크스페이스 조회
    @GetMapping("/get")
    public ResponseEntity<List<WorkspaceResponse>> getWorkspace() {
        List<WorkspaceResponse> workspaces = workspaceService.getWorkspace();
        return ResponseEntity.ok(workspaces);
    }

    // 워크스페이스 상세 조회
    @GetMapping("/{workspaceId}")
    public ResponseEntity<WorkspaceDetailResponse> getWorkspaceDetail(@PathVariable Long workspaceId) {
        WorkspaceDetailResponse workspaceDetail = workspaceService.getWorkspaceDetail(workspaceId);
        return ResponseEntity.ok(workspaceDetail);
    }

    // 워크스페이스 수정
    @PutMapping("/{workspaceId}")
    public ResponseEntity<Void> updateWorkspace(
            @PathVariable Long workspaceId,
            @RequestBody @Valid UpdateWorkspaceRequest request) {
        workspaceService.updateWorkspace(workspaceId, request);
        return ResponseEntity.ok().build();
    }

    // 워크스페이스 삭제
    @DeleteMapping("/{workspaceId}")  // DELETE 메서드 사용
    public ResponseEntity<Void> deleteWorkspace(@PathVariable Long workspaceId) {
        workspaceService.deleteWorkspace(workspaceId);
        return ResponseEntity.ok().build();
    }

    // 멤버 삭제
    @DeleteMapping("/{workspaceId}/member/{memberId}")
    public ResponseEntity<Void> deleteMember(
            @PathVariable Long workspaceId,
            @PathVariable Long memberId) {
        workspaceService.deleteMember(workspaceId, memberId);
        return ResponseEntity.ok().build();
    }

    // 멤버 초대
    // TODO: 초대 받은 사람이 이메일로 수락을 하면 멤버로 추가
    @PostMapping("/{workspaceId}/invite")
    public ResponseEntity<Void> inviteMember(
            @PathVariable Long workspaceId,
            @RequestBody @Valid InviteMemberRequest request) {
        workspaceService.inviteMember(workspaceId, request);
        return ResponseEntity.ok().build();
    }
}
