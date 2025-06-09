package com.sku.loom.domain.workspace.repository;

import com.sku.loom.domain.workspace.entity.WorkspaceProfiles;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WorkspaceProfileJpaRepository extends JpaRepository<WorkspaceProfiles, Long>, WorkspaceProfileCustomRepository {
}
