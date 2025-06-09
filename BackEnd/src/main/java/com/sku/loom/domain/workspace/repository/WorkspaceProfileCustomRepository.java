package com.sku.loom.domain.workspace.repository;

import com.sku.loom.domain.workspace.dto.response.WorkspaceResponse;

import java.util.List;

public interface WorkspaceProfileCustomRepository {
    List<WorkspaceResponse> findWorkspaceResponsesByUserId(Long userId);
}
