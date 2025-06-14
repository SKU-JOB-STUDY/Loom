package com.sku.loom.domain.channel.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ChannelCreateRequest {
    @Schema(description = "채널 이름", example = "채널 이름 20자 이하")
    private String channelName;
    @Schema(description = "채널 공개 여부", example = "1")
    private boolean channelOpened;
}
