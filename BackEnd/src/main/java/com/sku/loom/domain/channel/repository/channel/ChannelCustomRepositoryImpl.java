package com.sku.loom.domain.channel.repository.channel;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sku.loom.domain.channel.dto.response.ChannelResponse;
import com.sku.loom.domain.channel.entity.channel.Channels;
import com.sku.loom.domain.channel.entity.channel.QChannels;
import com.sku.loom.domain.channel.entity.profile_channel.QProfilesChannels;
import com.sku.loom.domain.channel.entity.section.QSections;
import com.sku.loom.domain.user.entity.QUsers;
import com.sku.loom.domain.workspace.entity.workspace.QWorkspaces;
import com.sku.loom.domain.workspace.entity.workspace_profile.QWorkspaceProfiles;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChannelCustomRepositoryImpl implements ChannelCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ChannelResponse> findChannelNameByUserId(long userId, long workspaceId) {
        QWorkspaceProfiles wp = QWorkspaceProfiles.workspaceProfiles;
        QUsers u = QUsers.users;
        QProfilesChannels pc = QProfilesChannels.profilesChannels;
        QChannels c = QChannels.channels;
        QSections s = QSections.sections;

        return queryFactory
                .select(Projections.constructor(ChannelResponse.class,
                        s.sectionName,
                        s.sectionIcon,
                        c.channelName,
                        c.channelDefault
                ))
                .from(u)
                .innerJoin(wp).on(u.userId.eq(wp.user.userId))
                .innerJoin(pc).on(wp.workspaceProfileId.eq(pc.workspaceProfile.workspaceProfileId))
                .innerJoin(c).on(pc.channel.channelId.eq(c.channelId))
                .leftJoin(s).on(c.section.sectionId.eq(s.sectionId))
                .where(
                        u.userId.eq(userId)
                                .and(wp.workspace.workspaceId.eq(workspaceId))
                )
                .orderBy(s.sectionName.asc(), c.channelName.asc())
                .fetch();
    }


    @Override
    public Channels findBasicChannelsByWorkspaceId(long workspaceId) {
        QChannels c = QChannels.channels;
        QProfilesChannels pc = QProfilesChannels.profilesChannels;
        QWorkspaceProfiles wp = QWorkspaceProfiles.workspaceProfiles;

        return queryFactory
                .select(c)
                .from(c)
                .join(pc).on(c.channelId.eq(pc.channel.channelId))
                .join(wp).on(pc.workspaceProfile.workspaceProfileId.eq(wp.workspaceProfileId))
                .where(wp.workspace.workspaceId.eq(workspaceId)
                        .and(c.channelDefault.eq(true)))
                .fetchFirst();
    }

    @Override
    public Channels findBasicChannelsByWorkspaceCode(String workspaceCode) {
        QChannels c = QChannels.channels;
        QProfilesChannels pc = QProfilesChannels.profilesChannels;
        QWorkspaceProfiles wp = QWorkspaceProfiles.workspaceProfiles;
        QWorkspaces w = QWorkspaces.workspaces;

        return queryFactory
                .select(c)
                .from(c)
                .join(pc).on(c.channelId.eq(pc.channel.channelId))
                .join(wp).on(pc.workspaceProfile.workspaceProfileId.eq(wp.workspaceProfileId))
                .join(w).on(wp.workspace.workspaceId.eq(w.workspaceId))
                .where(w.workspaceCode.eq(workspaceCode)
                        .and(c.channelDefault.eq(true)))
                .fetchFirst();
    }
}
