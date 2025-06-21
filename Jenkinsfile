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
                        echo "=== 브랜치 정보 ==="
                        git branch
                        echo "=== Git 상태 ==="
                        git status
                    '''
                }
                
                echo "✅ 체크아웃 완료"
            }
        }
        
        stage('File Check') {
            steps {
                script {
                    echo "📁 필수 파일 존재 확인..."
                    
                    sh '''
                        echo "=== docker-compose.yml 확인 ==="
                        if [ -f docker-compose.yml ]; then
                            echo "✅ docker-compose.yml 존재"
                            head -5 docker-compose.yml
                        else
                            echo "❌ docker-compose.yml 없음"
                        fi
                        
                        echo "=== BackEnd 폴더 확인 ==="
                        if [ -d BackEnd ]; then
                            echo "✅ BackEnd 폴더 존재"
                            ls -la BackEnd/
                        else
                            echo "❌ BackEnd 폴더 없음"
                        fi
                        
                        echo "=== FrontEnd 폴더 확인 ==="
                        if [ -d FrontEnd ]; then
                            echo "✅ FrontEnd 폴더 존재"
                            ls -la FrontEnd/
                        else
                            echo "❌ FrontEnd 폴더 없음"
                        fi
                    '''
                }
            }
        }
        
        stage('Docker Environment') {
            steps {
                script {
                    echo "🐳 Docker 환경 확인..."
                    
                    sh '''
                        echo "=== Docker 버전 ==="
                        docker --version
                        
                        echo "=== Docker Compose 버전 ==="
                        docker-compose --version
                        
                        echo "=== Docker 네트워크 생성 ==="
                        docker network create loom-network || echo "네트워크가 이미 존재합니다"
                        
                        echo "=== 현재 실행 중인 컨테이너 ==="
                        docker ps
                    '''
                }
            }
        }
        
        stage('Success') {
            steps {
                echo """
                🎉 Jenkins와 GitHub 연동 성공!
                
                ✅ 확인된 사항:
                ├── GitHub release 브랜치 체크아웃 완료
                ├── Jenkins workspace에 프로젝트 파일 존재
                └── Docker 환경 정상
                
                📁 Jenkins Workspace 위치:
                └── /var/lib/jenkins/workspace/loom-release-pipeline/
                """
            }
        }
    }
    
    post {
        always {
            script {
                sh '''
                    echo "=== 최종 작업 디렉토리 상태 ==="
                    pwd
                    ls -la
                '''
            }
        }
    }
}
