package com.example.notion.domain.workspace.controller;

import com.example.notion.domain.workspace.dto.request.CreateWorkspaceRequest;
import com.example.notion.domain.workspace.dto.request.InviteMemberRequest;
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

    @PostMapping("/{workspaceId}/invite")
    public ResponseEntity<Void> inviteMember(
            @PathVariable Long workspaceId,
            @RequestBody @Valid InviteMemberRequest request) {
        workspaceService.inviteMember(workspaceId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{workspaceId}")  // DELETE 메서드 사용
    public ResponseEntity<Void> deleteWorkspace(@PathVariable Long workspaceId) {
        workspaceService.deleteWorkspace(workspaceId);
        return ResponseEntity.ok().build();
    }
}
