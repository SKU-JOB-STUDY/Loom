package com.sku.loom.domain.workspace.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkspaceResponse {
    @Schema(description = "워크스페이스 이름", example = "워크스페이스")
    private String workspaceName;
    @Schema(description = "워크스페이스 이미지", example = "S3 url")
    private String workspaceImg;
}
