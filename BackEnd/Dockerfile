# 검색 결과 [4] 기반 - OpenJDK 21 사용
FROM openjdk:21-jdk-slim

LABEL maintainer="loom-team"
LABEL description="Loom Backend - Spring Boot with Java 21"

# 작업 디렉토리 설정
WORKDIR /app

# 필수 패키지 설치 (curl for health check)
RUN apt-get update && \
    apt-get install -y curl && \
    rm -rf /var/lib/apt/lists/* && \
    apt-get clean

# Gradle wrapper와 설정 파일들 복사 (캐시 최적화)
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# 실행 권한 부여
RUN chmod +x ./gradlew

# 의존성 다운로드 (레이어 캐싱 최적화)
RUN ./gradlew dependencies --no-daemon

# 소스 코드 복사
COPY src src

# 애플리케이션 빌드 (검색 결과 [2] - Java 21 호환 빌드)
RUN ./gradlew build --no-daemon -x test

# JAR 파일을 app.jar로 복사 (검색 결과 [4] 패턴)
RUN cp build/libs/*.jar app.jar

# 포트 노출
EXPOSE 8080

# Health check (검색 결과 [1] 권장사항)
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# JVM 최적화 옵션과 함께 실행 (검색 결과 [3] 최적화 적용)
ENTRYPOINT ["java", \
    "-Xms512m", \
    "-Xmx1024m", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-jar", "app.jar"]
