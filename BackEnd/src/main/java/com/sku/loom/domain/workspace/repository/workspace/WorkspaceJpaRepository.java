package com.sku.loom.domain.workspace.repository.workspace;

import com.sku.loom.domain.workspace.entity.workspace.Workspaces;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkspaceJpaRepository extends JpaRepository<Workspaces, Long> {
    boolean existsByWorkspaceCode(String workspaceCode);

    Optional<Workspaces> findByWorkspaceCode(String workspaceCode);
}
