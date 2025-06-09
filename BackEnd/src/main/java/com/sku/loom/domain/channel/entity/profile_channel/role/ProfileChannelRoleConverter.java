package com.sku.loom.domain.channel.entity.profile_channel.role;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ProfileChannelRoleConverter implements AttributeConverter<ProfileChannelRole, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ProfileChannelRole attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    @Override
    public ProfileChannelRole convertToEntityAttribute(Integer dbData) {
        return dbData != null ? ProfileChannelRole.fromCode(dbData) : null;
    }
}
