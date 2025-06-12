package com.sku.loom.domain.channel.repository.channel;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sku.loom.domain.channel.dto.response.ChannelResponse;
import com.sku.loom.domain.channel.entity.channel.Channels;
import com.sku.loom.domain.channel.entity.channel.QChannels;
import com.sku.loom.domain.channel.entity.profile_channel.QProfilesChannels;
import com.sku.loom.domain.user.entity.QUsers;
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

        return queryFactory
                .select(Projections.constructor(ChannelResponse.class, c.section, c.channelName, c.channelDefault))
                .from(u)
                .innerJoin(wp).on(u.userId.eq(wp.user.userId))
                .innerJoin(pc).on(wp.workspaceProfileId.eq(pc.workspaceProfile.workspaceProfileId))
                .innerJoin(c).on(pc.channel.channelId.eq(c.channelId))
                .where(
                        u.userId.eq(userId)
                                .and(wp.workspace.workspaceId.eq(workspaceId))
                                .and(c.channelOpened.eq(true))
                )
                .fetch();
    }

    @Override
    public Channels findChannelsByWorkspaceId(long workspaceId) {
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


}
