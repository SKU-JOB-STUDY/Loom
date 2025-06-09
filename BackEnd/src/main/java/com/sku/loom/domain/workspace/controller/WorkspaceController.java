package com.sku.loom.domain.workspace.controller;

import com.sku.loom.domain.workspace.dto.response.WorkspaceResponse;
import com.sku.loom.domain.workspace.service.WorkspaceService;
import com.sku.loom.global.dto.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/workspaces")
@RequiredArgsConstructor
@Log4j2
@Tag(name = "워크스페이스", description = "워크스페이스 관련 기능 수행")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @GetMapping
    @Operation(summary = "사용자 참여 워크스페이스 조회", description = "JWT을 사용해 회원의 전체 워크스페이스 조회")
    public ResponseEntity<BaseResponse<List<WorkspaceResponse>>> getWorkspaces(Authentication authentication) {
        List<WorkspaceResponse> response = workspaceService.getWorkspaces(Long.parseLong(authentication.getName()));

        return ResponseEntity.ok(BaseResponse.create(HttpStatus.OK.value(), "워크스페이스 정보를 성공적으로 조회했습니다.", response));
    }
}
