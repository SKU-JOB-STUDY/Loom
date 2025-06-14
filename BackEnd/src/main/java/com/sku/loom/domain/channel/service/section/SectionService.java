package com.sku.loom.domain.channel.service.section;

import com.sku.loom.domain.channel.dto.request.SectionCreateRequest;

public interface SectionService {
    void postSection(long userId, long workspaceId, SectionCreateRequest request);
}
