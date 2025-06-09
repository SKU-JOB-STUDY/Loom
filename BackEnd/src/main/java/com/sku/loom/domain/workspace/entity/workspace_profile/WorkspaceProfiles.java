package com.sku.loom.domain.workspace.entity.workspace_profile;

import com.sku.loom.domain.user.entity.Users;
import com.sku.loom.domain.workspace.entity.workspace_profile.role.WorkSpaceProfileRole;
import com.sku.loom.domain.workspace.entity.workspace_profile.role.WorkSpaceProfileRoleConverter;
import com.sku.loom.domain.workspace.entity.workspace.Workspaces;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "workspaces_profiles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class WorkspaceProfiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workspace_profile_id", nullable = false)
    @Schema(description = "워크스페이스-프로필 아이디", example = "1")
    private long workspaceProfileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Schema(description = "워크스페이스의 참여한 회원 아이디", example = "1")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false)
    @Schema(description = "워크스페이스의 아이디", example = "1")
    private Workspaces workspace;

    @Column(name = "workspace_profile_name", nullable = false)
    @Schema(description = "워크스페이스-프로필 이름", example = "이름 20자 이하")
    private String workspaceProfileName;

    @Column(name = "workspace_profile_img", nullable = false)
    @Schema(description = "워크스페이스-프로필 사진", example = "S3 url")
    private String workspaceProfileImg;

    @Column(name = "workspace_profile_comment", nullable = true)
    @Schema(description = "워크스페이스-프로필 소개", example = "소개글 30자 이하")
    private String workspaceProfileComment;

    @Column(name = "workspace_profile_role", nullable = true)
    @Convert(converter = WorkSpaceProfileRoleConverter.class)
    @Schema(description = "워크스페이스-프로필 역할", example = "0")
    private WorkSpaceProfileRole workSpaceProfileRole;

    @Column(name = "workspace_profile_created_at", nullable = false)
    @Schema(description = "워크스페이스-프로필 생성일자", example = "2025-06-09 00:00:00")
    private Timestamp workspaceProfileCreatedAt;

    @Column(name = "workspace_profile_updated_at", nullable = false)
    @Schema(description = "워크스페이스-프로필 수정일자", example = "2025-06-09 00:00:00")
    private Timestamp workspaceProfileUpdatedAt;

    @Override
    public String toString() {
        return "WorkspaceProfiles{" +
                "workspaceProfileId=" + workspaceProfileId +
                ", user=" + user +
                ", workspace=" + workspace +
                ", workspaceProfileName='" + workspaceProfileName + '\'' +
                ", workspaceProfileImg='" + workspaceProfileImg + '\'' +
                ", workspaceProfileComment='" + workspaceProfileComment + '\'' +
                ", workSpaceProfileRole=" + workSpaceProfileRole +
                ", workspaceProfileCreatedAt=" + workspaceProfileCreatedAt +
                ", workspaceProfileUpdatedAt=" + workspaceProfileUpdatedAt +
                '}';
    }
}
