package com.sku.loom.domain.channel.service.section;

import com.sku.loom.domain.channel.dto.request.ChannelSectionsUpdateRequest;
import com.sku.loom.domain.channel.dto.request.SectionCreateRequest;
import com.sku.loom.domain.channel.dto.response.SectionResponse;

import java.util.List;

public interface SectionService {
    // GET
    List<SectionResponse> getSections(long userId, long workspaceId);

    // POST
    void postSection(long userId, long workspaceId, SectionCreateRequest request);

    // PATCH
    void patchChannelSection(long userId, long workspaceId, ChannelSectionsUpdateRequest request);
}
