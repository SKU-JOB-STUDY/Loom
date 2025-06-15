package com.sku.loom.domain.channel.repository.channel;

import com.sku.loom.domain.channel.entity.channel.Channels;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChannelJpaRepository extends JpaRepository<Channels, Long> {
     Optional<Channels> findByChannelId(long channelId);
}
