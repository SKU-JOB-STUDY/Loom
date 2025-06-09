package com.sku.loom.domain.workspace.service;

import com.sku.loom.domain.workspace.dto.response.WorkspaceResponse;
import com.sku.loom.domain.workspace.repository.WorkspaceProfileJpaRepository;
import com.sku.loom.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class WorkspaceServiceImpl implements WorkspaceService{

    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceProfileJpaRepository workspaceProfileJpaRepository;

    @Override
    public List<WorkspaceResponse> getWorkspaces(Long userId) {
        return workspaceProfileJpaRepository.findWorkspaceResponsesByUserId(userId);
    }
}
