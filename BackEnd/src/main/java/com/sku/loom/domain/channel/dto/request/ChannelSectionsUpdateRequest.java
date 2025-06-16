package com.sku.loom.domain.channel.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ChannelSectionsUpdateRequest {
    @Schema(description = "채널 아이디", example = "1")
    private long channelId;
    @Schema(description = "섹션 아이디", example = "1")
    private Long sectionId;
}
