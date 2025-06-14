package com.sku.loom.domain.channel.service;

import com.sku.loom.domain.channel.dto.request.ChannelCreateRequest;
import com.sku.loom.domain.channel.dto.response.ChannelResponse;
import com.sku.loom.domain.channel.entity.channel.Channels;
import com.sku.loom.domain.channel.entity.profile_channel.ProfilesChannels;
import com.sku.loom.domain.channel.entity.profile_channel.role.ProfileChannelRole;
import com.sku.loom.domain.channel.repository.channel.ChannelCustomRepository;
import com.sku.loom.domain.channel.repository.channel.ChannelJpaRepository;
import com.sku.loom.domain.channel.repository.profile_channel.ProfileChannelJpaRepository;
import com.sku.loom.domain.workspace.entity.workspace_profile.WorkspaceProfiles;
import com.sku.loom.domain.workspace.repository.workspace_profile.WorkspaceProfileCustomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChannelServiceImpl implements ChannelService{

    private final ChannelJpaRepository channelJpaRepository;
    private final ChannelCustomRepository channelCustomRepository;
    private final ProfileChannelJpaRepository profileChannelJpaRepository;
    private final WorkspaceProfileCustomRepository workspaceProfileCustomRepository;


    @Override
    public List<ChannelResponse> getChannels(long userId, long workspaceId) {
        return channelCustomRepository.findChannelNameByUserId(userId, workspaceId);
    }

    @Override
    @Transactional
    public void postChannel(long userId, long workspaceId, ChannelCreateRequest request) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Channels newChannel = Channels.builder()
                .channelName(request.getChannelName())
                .channelOpened(request.isChannelOpened())
                .channelDefault(false)
                .build();
        channelJpaRepository.save(newChannel);

        WorkspaceProfiles workspaceProfile = workspaceProfileCustomRepository.findByUserIdAndWorkspaceId(userId, workspaceId);
        ProfilesChannels newProfileChannel = ProfilesChannels.builder()
                .workspaceProfile(workspaceProfile)
                .channel(newChannel)
                .profileChannelRole(ProfileChannelRole.OWNER)
                .build() ;
        profileChannelJpaRepository.save(newProfileChannel);
    }
}
