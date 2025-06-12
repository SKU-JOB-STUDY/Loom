package com.sku.loom.domain.channel.dto.response;

import com.sku.loom.domain.channel.entity.section.Sections;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChannelResponse {
    @Schema(description = "섹션 아이디", example = "1")
    private Sections section;
    @Schema(description = "채널 이름", example = "채널 이름 20자 이하")
    private String channelName;
    @Schema(description = "채널 기본 여부", example = "0")
    private boolean channelDefault;
}
