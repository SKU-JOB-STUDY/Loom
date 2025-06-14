package com.sku.loom.domain.channel.service.channel;

import com.sku.loom.domain.channel.dto.request.ChannelCreateRequest;
import com.sku.loom.domain.channel.dto.response.ChannelResponse;

import java.util.List;

public interface ChannelService {
    List<ChannelResponse> getChannels(long userId, long workspaceId);

    void postChannel(long userId, long workspaceId, ChannelCreateRequest request);
}
