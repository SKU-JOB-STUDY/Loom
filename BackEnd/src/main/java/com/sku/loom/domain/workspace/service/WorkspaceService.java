package com.sku.loom.domain.workspace.service;

import com.sku.loom.domain.workspace.dto.response.WorkspaceResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface WorkspaceService {
    List<WorkspaceResponse> getWorkspaces(Long userId);

    void postWorkspace(long userId, String workspaceName, MultipartFile image) throws IOException;
}
