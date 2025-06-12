package com.sku.loom.domain.channel.service;

import com.sku.loom.domain.channel.dto.response.ChannelResponse;

import java.util.List;

public interface ChannelService {
    List<ChannelResponse> getChannels(long userId, long workspaceId);
}
