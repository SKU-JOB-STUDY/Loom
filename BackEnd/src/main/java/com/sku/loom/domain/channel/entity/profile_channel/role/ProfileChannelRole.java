package com.sku.loom.domain.channel.entity.profile_channel.role;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProfileChannelRole {
    MEMBER(0),
    ADMIN(1),
    OWNER(2);

    private final int code;

    public static ProfileChannelRole fromCode(int code) {
        for (ProfileChannelRole role : values()) {
            if (role.code == code) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
