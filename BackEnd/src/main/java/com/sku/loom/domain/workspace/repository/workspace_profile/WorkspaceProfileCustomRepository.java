package com.sku.loom.domain.workspace.repository.workspace_profile;

import com.sku.loom.domain.workspace.dto.response.WorkspaceResponse;
import com.sku.loom.domain.workspace.entity.workspace_profile.role.WorkSpaceProfileRole;

import java.util.List;

public interface WorkspaceProfileCustomRepository {
    List<WorkspaceResponse> findWorkspaceResponsesByUserId(Long userId);

    WorkSpaceProfileRole findWorkspaceProfileRoleByUserIdAndWorkspaceId(long userId, long workspaceId);
}
