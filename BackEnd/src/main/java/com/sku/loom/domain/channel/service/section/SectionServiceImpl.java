package com.sku.loom.domain.channel.service.section;

import com.sku.loom.domain.channel.dto.request.SectionCreateRequest;
import com.sku.loom.domain.channel.dto.response.SectionResponse;
import com.sku.loom.domain.channel.entity.section.Sections;
import com.sku.loom.domain.channel.repository.section.SectionCustomRepository;
import com.sku.loom.domain.channel.repository.section.SectionJpaRepository;
import com.sku.loom.domain.workspace.entity.workspace_profile.WorkspaceProfiles;
import com.sku.loom.domain.workspace.repository.workspace_profile.WorkspaceProfileCustomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class SectionServiceImpl implements SectionService{

    private final SectionJpaRepository sectionJpaRepository;
    private final SectionCustomRepository sectionCustomRepository;
    private final WorkspaceProfileCustomRepository workspaceProfileCustomRepository;

    @Override
    public List<SectionResponse> getSections(long userId, long workspaceId) {
        return sectionCustomRepository.findSectionResponsesByUserIdAndWorkspaceId(userId, workspaceId);
    }

    @Override
    @Transactional
    public void postSection(long userId, long workspaceId, SectionCreateRequest request) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        WorkspaceProfiles targetWorkspaceProfile = workspaceProfileCustomRepository.findByUserIdAndWorkspaceId(userId, workspaceId);

        Sections newSection = Sections.builder()
                .workspaceProfile(targetWorkspaceProfile)
                .sectionName(request.getSectionName())
                .sectionIcon(request.getSectionIcon())
                .sectionCreatedAt(now)
                .build();
        sectionJpaRepository.save(newSection);
    }
}
