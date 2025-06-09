package com.sku.loom.domain.workspace.repository;

import com.sku.loom.domain.workspace.entity.Workspaces;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkspaceRepository extends JpaRepository<Workspaces, Long> {
    boolean existsByWorkspaceCode(String workspaceCode);

    Optional<Workspaces> findByWorkspaceCode(String workspaceCode);
}
