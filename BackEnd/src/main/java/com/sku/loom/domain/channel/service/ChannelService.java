package com.sku.loom.domain.channel.service;

import java.util.List;

public interface ChannelService {
    List<String> getChannels(long userId, long workspaceId);
}
