package com.sku.loom.domain.channel.repository.profile_channel;

import com.sku.loom.domain.channel.entity.profile_channel.ProfilesChannels;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileChannelJpaRepository extends JpaRepository<ProfilesChannels, Long> {
}