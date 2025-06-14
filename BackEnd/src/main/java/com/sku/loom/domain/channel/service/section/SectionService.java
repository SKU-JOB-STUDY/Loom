package com.sku.loom.domain.channel.service.section;

import com.sku.loom.domain.channel.dto.request.SectionCreateRequest;
import com.sku.loom.domain.channel.dto.response.SectionResponse;

import java.util.List;

public interface SectionService {
    List<SectionResponse> getSections(long userId, long workspaceId);
    void postSection(long userId, long workspaceId, SectionCreateRequest request);
}
