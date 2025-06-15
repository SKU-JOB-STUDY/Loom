// com.sku.loom.global.filter.JwtAuthenticationFilter 수정
package com.sku.loom.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sku.loom.global.exception.base.CustomException;
import com.sku.loom.global.exception.constant.ErrorDetail;
import com.sku.loom.global.service.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        log.info("=== JWT Filter 시작 ===");
        log.info("요청 URI: {}", requestURI);
        log.info("HTTP Method: {}", method);

        // 1. OPTIONS 요청이나 제외 경로는 필터 건너뛰기
        if ("OPTIONS".equals(method) || isExcludePath(requestURI, method)) {
            log.info("제외 경로 또는 OPTIONS 요청: {} {}", method, requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 2. Authorization 헤더에서 JWT 토큰 추출
            String token = resolveToken(request);
            log.info("추출된 토큰: {}", token != null ? "존재함" : "없음");

            if (token != null) {
                log.info("토큰 내용: {}", token.substring(0, Math.min(token.length(), 50)) + "...");
            }

            // 3. 토큰 검증 및 인증 정보 설정
            if (StringUtils.hasText(token)) {
                if (jwtTokenProvider.validateToken(token)) {
                    Authentication authentication = getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("✅ JWT 인증 성공 - 사용자: {}", authentication.getName());
                } else {
                    log.warn("❌ JWT 토큰 검증 실패");
                    sendErrorResponse(response, "유효하지 않은 토큰입니다.");
                    return;
                }
            } else {
                log.warn("❌ JWT 토큰이 없습니다.");
                sendErrorResponse(response, "인증 토큰이 필요합니다.");
                return;
            }

        } catch (Exception e) {
            log.error("❌ JWT 인증 처리 중 오류: {}", e.getMessage(), e);
            SecurityContextHolder.clearContext();
            sendErrorResponse(response, "인증 처리 중 오류가 발생했습니다.");
            return;
        }

        log.info("=== JWT Filter 완료 ===");
        filterChain.doFilter(request, response);
    }

    /**
     * 제외 경로 확인 (정확한 매칭)
     */
    private boolean isExcludePath(String requestURI, String method) {
        // Swagger 관련 경로
        if (requestURI.startsWith("/swagger-ui") ||
                requestURI.startsWith("/v3/api-docs") ||
                requestURI.startsWith("/swagger-resources") ||
                requestURI.startsWith("/webjars") ||
                requestURI.equals("/favicon.ico")) {
            return true;
        }

        // 정확한 경로와 메서드 매칭
        if ("POST".equals(method) && "/api/users/email/send".equals(requestURI)) {
            log.debug("제외: 이메일 발송 API");
            return true;
        }

        if ("POST".equals(method) && "/api/users".equals(requestURI)) {
            log.debug("제외: 회원가입/로그인 API");
            return true;
        }

        if ("POST".equals(method) && "/api/users/refresh".equals(requestURI)) {
            log.debug("제외: 토큰 갱신 API");
            return true;
        }

        log.debug("인증 필요: {} {}", method, requestURI);
        return false;
    }

    /**
     * Authorization 헤더에서 Bearer 토큰 추출
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        log.debug("Authorization 헤더: {}", bearerToken);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * JWT 토큰으로부터 Authentication 객체 생성
     */
    private Authentication getAuthentication(String token) {
        try {
            Long userId = jwtTokenProvider.getUserIdFromToken(token);

            log.info("JWT 파싱 결과 - userId: {}", userId);

            if (userId == null) {
                throw new CustomException(ErrorDetail.EMPTY_SUBJECT);
            }

            log.info("Authentication 생성 전 - principal로 사용할 값: {}, 타입: {}",
                    userId, userId.getClass().getSimpleName());

            Authentication auth = new UsernamePasswordAuthenticationToken(
                    userId.toString(),
                    null,
                    List.of()
            );

            log.info("Authentication 생성 직후 - principal: {}, getName(): {}",
                    auth.getPrincipal(), auth.getName());

            return auth;
        } catch (Exception e) {
            log.error("JWT 토큰 파싱 실패: {}", e.getMessage());
            throw new RuntimeException("JWT 토큰 파싱 실패", e);
        }
    }

    /**
     * 에러 응답 전송
     */
    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("errorCode", "T0007");
        errorResponse.put("errorMessage", message);

        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(errorResponse));
        writer.flush();
    }
}
