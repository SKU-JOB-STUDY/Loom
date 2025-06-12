package com.sku.loom.domain.workspace.repository.workspace_profile;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sku.loom.domain.workspace.dto.response.WorkspaceResponse;
import com.sku.loom.domain.workspace.entity.workspace.QWorkspaces;
import com.sku.loom.domain.workspace.entity.workspace_profile.QWorkspaceProfiles;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WorkspaceProfileCustomRepositoryImpl implements WorkspaceProfileCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<WorkspaceResponse> findWorkspaceResponsesByUserId(Long userId) {
        QWorkspaceProfiles wp = QWorkspaceProfiles.workspaceProfiles;
        QWorkspaces w = QWorkspaces.workspaces;

        return queryFactory
                .select(Projections.constructor(WorkspaceResponse.class, w.workspaceName, w.workspaceImg))
                .from(wp)
                .join(wp.workspace, w)
                .where(wp.user.userId.eq(userId))
                .fetch();
    }
}
