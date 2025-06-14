package com.sku.loom.domain.channel.service.section;

import com.sku.loom.domain.channel.dto.request.SectionCreateRequest;
import com.sku.loom.domain.channel.entity.section.Sections;
import com.sku.loom.domain.channel.repository.section.SectionJpaRepository;
import com.sku.loom.domain.workspace.entity.workspace_profile.WorkspaceProfiles;
import com.sku.loom.domain.workspace.repository.workspace_profile.WorkspaceProfileCustomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class SectionServiceImpl implements SectionService{

    private final SectionJpaRepository sectionJpaRepository;
    private final WorkspaceProfileCustomRepository workspaceProfileCustomRepository;

    @Override
    @Transactional
    public void postSection(long userId, long workspaceId, SectionCreateRequest request) {
        WorkspaceProfiles targetWorkspaceProfile = workspaceProfileCustomRepository.findByUserIdAndWorkspaceId(userId, workspaceId);

        Sections newSection = Sections.builder()
                .workspaceProfile(targetWorkspaceProfile)
                .sectionName(request.getSectionName())
                .sectionIcon(request.getSectionIcon())
                .build();
        sectionJpaRepository.save(newSection);
    }
}
