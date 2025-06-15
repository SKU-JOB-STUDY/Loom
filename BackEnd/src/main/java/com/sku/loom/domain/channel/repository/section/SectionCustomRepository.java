package com.sku.loom.domain.channel.repository.section;

import com.sku.loom.domain.channel.dto.response.SectionResponse;

import java.util.List;

public interface SectionCustomRepository {
    List<SectionResponse> findSectionResponsesByUserIdAndWorkspaceId(long userId, long workspaceId);
    boolean existsBySectionIdAndUserIdAndWorkspaceId(long sectionId, long workspaceId, long userId);
}
