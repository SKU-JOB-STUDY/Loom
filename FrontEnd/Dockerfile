# Node 22 개발 서버 (nginx 제거!)
FROM node:22-alpine

WORKDIR /app

# package.json과 package-lock.json 복사
COPY package*.json ./

# 의존성 설치
RUN npm ci

# 소스 코드 복사
COPY . .

# 포트 노출
EXPOSE 3000

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
  CMD curl -f http://localhost:3000 || exit 1

# React 개발 서버 실행 (nginx 없이!)
CMD ["npm", "start"]
