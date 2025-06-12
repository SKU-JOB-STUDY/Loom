package com.sku.loom.domain.channel.repository.channel;

import com.sku.loom.domain.channel.dto.response.ChannelResponse;

import java.util.List;

public interface ChannelCustomRepository {
    List<ChannelResponse> findChannelNameByUserId(long userId, long workspaceId);
}
