package com.sku.loom.domain.channel.controller;

import com.sku.loom.domain.channel.service.ChannelService;
import com.sku.loom.global.dto.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@Tag(name = "채널", description = "채널 관련 기능 수행")
public class ChannelController {

    private final ChannelService channelService;

    @GetMapping("/api/workspaces/{workspaceId}/channels")
    @Operation(summary = "사용자 참여 채널 조회", description = "JWT를 사용해 회원의 참여한 전체 채널 조회")
    public ResponseEntity<BaseResponse<List<String>>> getChannels(Authentication authentication,
                                                                  @PathVariable("workspaceId") Long workspaceId) {
        List<String> response = channelService.getChannels(Long.parseLong(authentication.getName()), workspaceId);

        return ResponseEntity.ok(BaseResponse.create(HttpStatus.OK.value(), "참여한 채널 이름을 성공적으로 조회했습니다.", response));
    }
}
