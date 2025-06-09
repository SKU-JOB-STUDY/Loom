package com.sku.loom.domain.workspace.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WorkSpaceProfileRole {
    MEMBER(0),
    OWNER(1);

    private final int code;

    public static WorkSpaceProfileRole fromCode(int code) {
        for (WorkSpaceProfileRole role : values()) {
            if (role.code == code) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
