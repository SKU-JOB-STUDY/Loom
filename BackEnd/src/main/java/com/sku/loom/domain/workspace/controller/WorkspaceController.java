package com.sku.loom.domain.workspace.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workspaces")
@RequiredArgsConstructor
@Log4j2
@Tag(name = "워크스페이스", description = "워크스페이스 관련 기능 수행")
public class WorkspaceController {

    private final WorkspaceService workspaceService;
}
