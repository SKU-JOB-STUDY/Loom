package com.sku.loom.domain.channel.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SectionResponse {
    @Schema(description = "섹션 아이디", example = "1")
    private long sectionId;
    @Schema(description = "섹션 이름", example = "이름 10자 이하")
    private String sectionName;
    @Schema(description = "섹션 아이콘", example = "아이콘 이름 50자 이하")
    private String sectionIcon;
}
