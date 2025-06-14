package com.sku.loom.domain.channel.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class SectionCreateRequest {
    @Schema(description = "섹션 이름", example = "이름 10자 이하")
    private String sectionName;
    @Schema(description = "섹션 아이콘", example = "아이콘 이름 50자 이하")
    private String sectionIcon;
}
