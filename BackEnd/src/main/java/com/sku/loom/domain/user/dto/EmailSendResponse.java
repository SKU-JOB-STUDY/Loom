// com.sku.loom.domain.user.dto.EmailSendResponse
package com.sku.loom.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "이메일 인증코드 발송 응답")
public class EmailSendResponse {

    @Schema(description = "성공 메시지", example = "인증코드가 발송되었습니다.")
    private String message;

    @Schema(description = "인증코드", example = "123456")
    private String verificationCode;
}
