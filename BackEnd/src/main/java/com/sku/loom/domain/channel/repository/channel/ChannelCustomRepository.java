package com.sku.loom.domain.channel.repository.channel;

import java.util.List;

public interface ChannelCustomRepository {
    List<String> findChannelNameByUserId(long userId, long workspaceId);
}
