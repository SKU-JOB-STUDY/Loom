# Node 22 사용
FROM node:22-alpine AS build

WORKDIR /app

# package.json과 package-lock.json 복사
COPY package*.json ./

# 의존성 설치
RUN npm ci --only=production

# 소스 코드 복사
COPY . .

# React 앱 빌드
RUN npm run build

# Nginx 단계
FROM nginx:alpine

# 빌드된 React 앱 복사
COPY --from=build /app/build /usr/share/nginx/html

# 커스텀 nginx 설정 복사
COPY nginx.conf /etc/nginx/conf.d/default.conf

# 포트 노출
EXPOSE 3000

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
  CMD curl -f http://localhost:3000 || exit 1

# Nginx 실행
CMD ["nginx", "-g", "daemon off;"]
