package com.sku.loom.domain.channel.dto.response;

import com.sku.loom.domain.channel.entity.section.Sections;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelResponse {
    @Schema(description = "섹션 이름", example = "이름 10자 이하")
    private String sectionName;
    @Schema(description = "섹션 아이콘", example = "아이콘 이름 50자 이하")
    private String sectionIcon;
    @Schema(description = "채널 이름", example = "채널 이름 20자 이하")
    private String channelName;
    @Schema(description = "채널 기본 여부", example = "0")
    private boolean channelDefault;
}
