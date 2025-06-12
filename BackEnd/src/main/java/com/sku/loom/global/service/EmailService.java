package com.sku.loom.global.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailService {

    private final JavaMailSender mailSender;

    // Redis 대신 메모리 Map 사용 (개발용)
    private final ConcurrentHashMap<String, CodeData> codeStorage = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private static final String AUTH_CODE_PREFIX = "AUTH_CODE:";
    private static final int CODE_LENGTH = 6;
    private static final int CODE_EXPIRE_MINUTES = 3;

    // 인증코드와 만료시간을 저장하는 내부 클래스
    private static class CodeData {
        private final String code;
        private final LocalDateTime expiryTime;

        public CodeData(String code) {
            this.code = code;
            this.expiryTime = LocalDateTime.now().plusMinutes(CODE_EXPIRE_MINUTES);
        }

        public boolean isExpired() {
            return LocalDateTime.now().isAfter(expiryTime);
        }

        public String getCode() {
            return code;
        }
    }

    public void sendVerificationCode(String email) {
        String code = generateRandomCode();

        // 메모리에 저장
        String key = AUTH_CODE_PREFIX + email;
        codeStorage.put(key, new CodeData(code));

        // 3분 후 자동 삭제 스케줄링
        scheduler.schedule(() -> {
            codeStorage.remove(key);
            log.info("만료된 인증코드 삭제: {}", email);
        }, CODE_EXPIRE_MINUTES, TimeUnit.MINUTES);

        // 이메일 발송 (실제 발송 대신 콘솔 출력)
        sendEmail(email, code);

        log.info("인증코드 발송 완료: {} -> {}", email, code);
    }

    public boolean verifyCode(String email, String code) {
        String key = AUTH_CODE_PREFIX + email;
        CodeData storedData = codeStorage.get(key);

        if (storedData == null) {
            log.warn("인증코드 검증 실패: {} -> 코드가 존재하지 않음", email);
            return false;
        }

        if (storedData.isExpired()) {
            codeStorage.remove(key);
            log.warn("인증코드 검증 실패: {} -> 코드가 만료됨", email);
            return false;
        }

        boolean isValid = storedData.getCode().equals(code);

        if (isValid) {
            codeStorage.remove(key);
            log.info("인증코드 검증 성공: {}", email);
        } else {
            log.warn("인증코드 검증 실패: {} -> 입력: {}, 저장: {}", email, code, storedData.getCode());
        }

        return isValid;
    }

    private String generateRandomCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(random.nextInt(10));
        }

        return code.toString();
    }

    private void sendEmail(String to, String code) {
        // 개발용: 실제 이메일 발송 대신 콘솔 출력
        log.info("=== 개발용 이메일 발송 ===");
        log.info("받는 사람: {}", to);
        log.info("제목: [Loom] 이메일 인증코드");
        log.info("인증코드: {}", code);
        log.info("만료시간: {}분", CODE_EXPIRE_MINUTES);
        log.info("========================");

        // 실제 이메일 발송이 필요하면 아래 주석 해제
        /*
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("[Loom] 이메일 인증코드");
            message.setText(createEmailContent(code));
            message.setFrom("kimloom1999@gmail.com");

            mailSender.send(message);
            log.info("이메일 발송 성공: {}", to);
        } catch (Exception e) {
            log.error("이메일 발송 실패: {} -> {}", to, e.getMessage());
            throw new UserException(ErrorDetail.EMAIL_SEND_FAILED);
        }
        */
    }

    private String createEmailContent(String code) {
        return String.format(
                "안녕하세요! Loom입니다.\n\n" +
                        "아래 인증코드를 입력해 주세요.\n\n" +
                        "인증코드: %s\n\n" +
                        "이 코드는 %d분 후 만료됩니다.\n\n" +
                        "감사합니다.",
                code, CODE_EXPIRE_MINUTES
        );
    }
}
