pipeline {
    agent any
    
    tools {
        nodejs 'node22'
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo "🔍 Release 브랜치 체크아웃 시작..."
                checkout scm
                
                script {
                    sh '''
                        echo "=== 현재 작업 디렉토리 ==="
                        pwd
                        echo "=== 프로젝트 파일 확인 ==="
                        ls -la
                    '''
                }
                
                echo "✅ 체크아웃 완료"
            }
        }
        
        stage('Docker Environment') {
            steps {
                script {
                    echo "🐳 Docker 환경 확인..."
                    
                    sh '''
                        echo "=== Docker 버전 ==="
                        docker --version
                        
                        echo "=== Docker Compose 버전 (v2) ==="
                        docker compose version  # ← 하이픈 없음!
                        
                        echo "=== Docker 네트워크 생성 ==="
                        docker network create loom-network || echo "네트워크가 이미 존재합니다"
                        
                        echo "=== 현재 실행 중인 컨테이너 ==="
                        docker ps
                    '''
                }
            }
        }
        
        stage('Test Deploy') {
            steps {
                script {
                    echo "🧪 테스트 배포..."
                    
                    sh '''
                        echo "=== Docker Compose 파일 검증 ==="
                        docker compose config
                        
                        echo "=== 데이터베이스 서비스만 시작 ==="
                        docker compose up -d mysql redis mongodb
                        
                        echo "⏳ 서비스 시작 대기..."
                        sleep 30
                        
                        echo "📊 컨테이너 상태 확인..."
                        docker compose ps
                    '''
                }
            }
        }
        
        stage('Success') {
            steps {
                echo """
                🎉 Docker Compose 문제 해결 성공!
                
                ✅ 확인된 사항:
                ├── Docker: 정상 작동
                ├── Docker Compose v2: 정상 작동
                ├── 프로젝트 파일: 모두 존재
                └── 기본 서비스: 시작 완료
                
                🚀 다음 단계: 전체 애플리케이션 빌드 및 배포
                """
            }
        }
    }
    
    post {
        failure {
            script {
                sh '''
                    echo "=== 디버깅 정보 ==="
                    docker ps -a || true
                    docker compose ps || true
                '''
            }
        }
        always {
            sh 'docker image prune -f || true'
        }
    }
}
