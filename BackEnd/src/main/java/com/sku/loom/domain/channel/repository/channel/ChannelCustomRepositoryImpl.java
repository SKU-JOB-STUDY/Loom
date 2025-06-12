package com.sku.loom.domain.channel.repository.channel;

import com.querydsl.jpa.impl.JPAQueryFactory;
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
    public List<String> findChannelNameByUserId(long userId, long workspaceId) {
        QWorkspaceProfiles wp = QWorkspaceProfiles.workspaceProfiles;
        QUsers u = QUsers.users;
        QProfilesChannels pc = QProfilesChannels.profilesChannels;
        QChannels c = QChannels.channels;

        return queryFactory
                .select(c.channelName)
                .from(u)
                .innerJoin(wp).on(u.userId.eq(wp.user.userId))
                .innerJoin(pc).on(wp.workspaceProfileId.eq(pc.workspaceProfile.workspaceProfileId))
                .innerJoin(c).on(pc.channel.channelId.eq(c.channelId))
                .where(
                        u.userId.eq(userId)
                                .and(wp.workspace.workspaceId.eq(workspaceId))
                )
                .fetch();
    }
}
