package com.sku.loom.domain.workspace.service;

import com.sku.loom.domain.workspace.dto.response.WorkspaceResponse;

import java.util.List;

public interface WorkspaceService {
    List<WorkspaceResponse> getWorkspaces(Long userId);
}
