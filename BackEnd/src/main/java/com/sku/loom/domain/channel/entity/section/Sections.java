package com.sku.loom.domain.channel.entity.section;


import com.sku.loom.domain.workspace.entity.workspace.Workspaces;
import com.sku.loom.domain.workspace.entity.workspace_profile.WorkspaceProfiles;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "sections")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Sections {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id", nullable = false)
    @Schema(description = "섹션 아이디", example = "1")
    private long sectionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_profile_id", nullable = false)
    @Schema(description = "워크스페이스 프로필의 아이디", example = "1")
    private WorkspaceProfiles workspaceProfile;

    @Column(name = "section_name", nullable = false)
    @Schema(description = "섹션 이름", example = "이름 10자 이하")
    private String sectionName;

    @Column(name = "section_icon", nullable = true)
    @Schema(description = "섹션 아이콘", example = "아이콘 이름 50자 이하")
    private String sectionIcon;

    @Column(name = "section_created_at", nullable = false)
    @Schema(description = "섹션 생성일자", example = "2025-06-09 00:00:00")
    private Timestamp sectionCreatedAt;

    @Override
    public String toString() {
        return "Sections{" +
                "sectionId=" + sectionId +
                ", sectionName='" + sectionName + '\'' +
                ", sectionIcon='" + sectionIcon + '\'' +
                ", sectionCreatedAt=" + sectionCreatedAt +
                '}';
    }
}
