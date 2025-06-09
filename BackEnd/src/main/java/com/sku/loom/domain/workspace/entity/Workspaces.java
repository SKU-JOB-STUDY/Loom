package com.sku.loom.domain.workspace.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "workspaces")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Workspaces {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workspace_id", nullable = false)
    @Schema(description = "워크스페이스 아이디", example = "1")
    private long workspaceId;

    @Column(name = "workspace_name", nullable = false)
    @Schema(description = "워크스페이스 이름", example = "워크스페이스 이름")
    private String workspaceName;

    @Column(name = "workspace_img", nullable = false)
    @Schema(description = "워크스페이스 이미지", example = "S3 url")
    private String workspaceImg;

    @Column(name = "workspace_code", nullable = false)
    @Schema(description = "워크스페이스 초대 코드", example = "ABCEFG")
    private String workspaceCode;

    @Column(name = "workspace_created_at", nullable = false)
    @Schema(description = "워크스페이스 생성일자", example = "2025-06-09 00:00:00")
    private Timestamp workspaceCreatedAt;

    @Column(name = "workspace_updated_at", nullable = false)
    @Schema(description = "워크스페이스 생성일자", example = "2025-06-09 00:00:00")
    private Timestamp workspaceUpdatedAt;

    @Override
    public String toString() {
        return "Workspaces{" +
                "workspaceId=" + workspaceId +
                ", workspaceName='" + workspaceName + '\'' +
                ", workspaceImg='" + workspaceImg + '\'' +
                ", workspaceCode='" + workspaceCode + '\'' +
                ", workspaceCreatedAt=" + workspaceCreatedAt +
                ", workspaceUpdatedAt=" + workspaceUpdatedAt +
                '}';
    }
}
