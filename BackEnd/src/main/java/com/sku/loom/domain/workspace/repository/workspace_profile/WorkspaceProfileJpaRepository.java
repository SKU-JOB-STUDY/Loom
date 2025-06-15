package com.sku.loom.domain.workspace.repository.workspace_profile;

import com.sku.loom.domain.user.entity.Users;
import com.sku.loom.domain.workspace.entity.workspace.Workspaces;
import com.sku.loom.domain.workspace.entity.workspace_profile.WorkspaceProfiles;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WorkspaceProfileJpaRepository extends JpaRepository<WorkspaceProfiles, Long> {
     boolean existsByUserAndWorkspace(Users user, Workspaces workspace) ;
}
