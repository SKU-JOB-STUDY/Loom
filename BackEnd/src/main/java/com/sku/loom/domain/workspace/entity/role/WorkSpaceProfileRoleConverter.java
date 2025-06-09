package com.sku.loom.domain.workspace.entity.role;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class WorkSpaceProfileRoleConverter implements AttributeConverter<WorkSpaceProfileRole, Integer> {

    @Override
    public Integer convertToDatabaseColumn(WorkSpaceProfileRole attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    @Override
    public WorkSpaceProfileRole convertToEntityAttribute(Integer dbData) {
        return dbData != null ? WorkSpaceProfileRole.fromCode(dbData) : null;
    }
}