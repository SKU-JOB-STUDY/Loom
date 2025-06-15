package com.sku.loom.domain.channel.repository.section;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sku.loom.domain.channel.dto.response.SectionResponse;
import com.sku.loom.domain.channel.entity.section.QSections;
import com.sku.loom.domain.user.entity.QUsers;
import com.sku.loom.domain.workspace.entity.workspace_profile.QWorkspaceProfiles;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SectionCustomRepositoryImpl implements SectionCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<SectionResponse> findSectionResponsesByUserIdAndWorkspaceId(long userId, long workspaceId) {
        QUsers u = QUsers.users;
        QWorkspaceProfiles wp = QWorkspaceProfiles.workspaceProfiles;
        QSections s = QSections.sections;

        return queryFactory
                .select(Projections.constructor(SectionResponse.class, s.sectionId, s.sectionName, s.sectionIcon))
                .from(s)
                .join(s.workspaceProfile, wp)
                .join(wp.user, u)
                .where(u.userId.eq(userId)
                        .and(wp.workspace.workspaceId.eq(workspaceId)))
                .fetch();
    }

    @Override
    public boolean existsBySectionIdAndUserIdAndWorkspaceId(long sectionId, long workspaceId, long userId) {
        QSections s = QSections.sections;
        QWorkspaceProfiles wp = QWorkspaceProfiles.workspaceProfiles;

        return queryFactory
                .selectOne()
                .from(s)
                .join(wp).on(s.workspaceProfile.workspaceProfileId.eq(wp.workspaceProfileId))
                .where(
                        s.sectionId.eq(sectionId)
                                .and(wp.workspace.workspaceId.eq(workspaceId))
                                .and(wp.user.userId.eq(userId))
                )
                .fetchFirst() != null;
    }
}
