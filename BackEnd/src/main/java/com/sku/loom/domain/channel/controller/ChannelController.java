package com.sku.loom.domain.channel.controller;

import com.sku.loom.domain.channel.dto.request.ChannelCreateRequest;
import com.sku.loom.domain.channel.dto.request.SectionCreateRequest;
import com.sku.loom.domain.channel.dto.response.ChannelResponse;
import com.sku.loom.domain.channel.service.channel.ChannelService;
import com.sku.loom.domain.channel.service.section.SectionService;
import com.sku.loom.global.dto.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workspaces")
@RequiredArgsConstructor
@Log4j2
@Tag(name = "채널", description = "채널 관련 기능 수행")
public class ChannelController {

    private final ChannelService channelService;
    private final SectionService sectionService;

    @GetMapping("/{workspaceId}/channels")
    @Operation(summary = "사용자 참여 채널 조회", description = "JWT를 사용해 회원의 참여한 전체 채널 조회")
    public ResponseEntity<BaseResponse<List<ChannelResponse>>> getChannels(Authentication authentication,
                                                                           @PathVariable("workspaceId") Long workspaceId) {
        List<ChannelResponse> response = channelService.getChannels(Long.parseLong(authentication.getName()), workspaceId);

        return ResponseEntity.ok(BaseResponse.create(HttpStatus.OK.value(), "참여한 채널 이름을 성공적으로 조회했습니다.", response));
    }

    @PostMapping("/{workspaceId}/channels")
    @Operation(summary = "채널 생성", description = "workspaceId에 해당하는 워크스페이스 내 채널 생성")
    public ResponseEntity<BaseResponse<Void>> postChannel(Authentication authentication,
                                                                @PathVariable("workspaceId") long workspaceId,
                                                                @RequestBody ChannelCreateRequest request) {
        channelService.postChannel(Long.parseLong(authentication.getName()), workspaceId, request);

        return ResponseEntity.ok((BaseResponse.create(HttpStatus.CREATED.value(), "채널을 성공적으로 생성했습니다.")));
    }

    @PostMapping("/{workspaceId}/sections")
    @Operation(summary = "채널 섹션 생성", description = "workspaceId에 해당하는 워크스페이스 내 섹션 생성")
    public ResponseEntity<BaseResponse<Void>> postSection(Authentication authentication,
                                                          @PathVariable("workspaceId") long workspaceId,
                                                          @RequestBody SectionCreateRequest request) {
        sectionService.postSection(Long.parseLong(authentication.getName()), workspaceId, request);

        return ResponseEntity.ok(BaseResponse.create(HttpStatus.CREATED.value(), "섹션을 성공적으로 생성했습니다."));
    }
}
