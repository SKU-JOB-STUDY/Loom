package com.sku.loom.domain.channel.service;

import com.sku.loom.domain.channel.repository.channel.ChannelCustomRepository;
import com.sku.loom.domain.channel.repository.channel.ChannelJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChannelServiceImpl implements ChannelService{

    private final ChannelJpaRepository channelJpaRepository;
    private final ChannelCustomRepository channelCustomRepository;

    @Override
    public List<String> getChannels(long userId, long workspaceId) {
        return channelCustomRepository.findChannelNameByUserId(userId, workspaceId);
    }
}
