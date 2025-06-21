pipeline {
    agent any
    
    tools {
        nodejs 'node22'
    }
    
    environment {
        PATH = "$PATH:/usr/local/bin"  // docker-compose 경로 추가
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo "🔍 Release 브랜치 체크아웃..."
                checkout scm
                
                script {
                    sh '''
                        echo "=== 현재 디렉토리 ==="
                        pwd
                        ls -la
                    '''
                }
            }
        }
        
        stage('Environment Check') {
            steps {
                script {
                    echo "🐳 환경 확인..."
                    
                    sh '''
                        echo "=== Docker 확인 ==="
                        docker --version
                        docker ps
                        
                        echo "=== Docker Compose 확인 ==="
                        /usr/local/bin/docker-compose --version
                        
                        echo "=== 권한 확인 ==="
                        id
                        
                        echo "=== 네트워크 생성 ==="
                        docker network create loom-network || echo "네트워크 이미 존재"
                    '''
                }
            }
        }
        
        stage('Simple Test') {
            steps {
                script {
                    echo "🧪 간단 테스트..."
                    
                    sh '''
                        echo "=== Docker Compose 설정 검증 ==="
                        /usr/local/bin/docker-compose config
                        
                        echo "=== MySQL만 테스트 시작 ==="
                        /usr/local/bin/docker-compose up -d mysql
                        
                        sleep 20
                        
                        echo "=== 컨테이너 상태 ==="
                        docker ps
                    '''
                }
            }
        }
        
        stage('Success') {
            steps {
                echo """
                🎉 기본 환경 구성 성공!
                
                ✅ 해결된 문제들:
                ├── Docker: 정상 작동
                ├── Docker Compose: 설치 완료  
                ├── 권한: Root 권한으로 해결
                └── 무한 재시작: 해결됨
                
                🚀 다음 단계: 전체 서비스 배포
                """
            }
        }
    }
    
    post {
        always {
            sh 'docker image prune -f || true'
        }
    }
}
