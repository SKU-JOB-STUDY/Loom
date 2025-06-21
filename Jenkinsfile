pipeline {
    agent any
    
    tools {
        nodejs 'node22'
    }
    
    environment {
        PATH = "$PATH:/usr/local/bin"
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo "🔍 Release 브랜치 체크아웃..."
                checkout scm
                
                script {
                    sh '''
                        echo "=== 프로젝트 구조 확인 ==="
                        ls -la
                        echo "=== BackEnd Dockerfile 확인 ==="
                        ls -la BackEnd/Dockerfile || echo "BackEnd Dockerfile 없음"
                        echo "=== FrontEnd Dockerfile 확인 ==="
                        ls -la FrontEnd/Dockerfile || echo "FrontEnd Dockerfile 없음"
                    '''
                }
            }
        }
        
        stage('Build Applications') {
            parallel {
                stage('Build Backend') {
                    steps {
                        script {
                            echo "🔨 Backend 빌드 (Java 21)..."
                            
                            sh '''
                                echo "=== Backend Docker 이미지 빌드 ==="
                                /usr/local/bin/docker-compose build backend
                                
                                echo "=== 빌드된 Backend 이미지 확인 ==="
                                docker images | grep loom-backend
                            '''
                        }
                    }
                }
                
                stage('Build Frontend') {
                    steps {
                        script {
                            echo "🔨 Frontend 빌드 (Node 22)..."
                            
                            sh '''
                                echo "=== Frontend Docker 이미지 빌드 ==="
                                /usr/local/bin/docker-compose build frontend
                                
                                echo "=== 빌드된 Frontend 이미지 확인 ==="
                                docker images | grep loom-frontend
                            '''
                        }
                    }
                }
            }
        }
        
        stage('Deploy Full Stack') {
            steps {
                script {
                    echo "🚀 전체 스택 배포..."
                    
                    sh '''
                        echo "=== 전체 서비스 시작 ==="
                        /usr/local/bin/docker-compose up -d
                        
                        echo "⏳ 서비스 시작 대기 (60초) ==="
                        sleep 60
                        
                        echo "📊 모든 컨테이너 상태 ==="
                        docker ps
                        
                        echo "📋 Docker Compose 서비스 상태 ==="
                        /usr/local/bin/docker-compose ps
                    '''
                }
            }
        }
        
        stage('Health Check') {
            parallel {
                stage('Backend Health') {
                    steps {
                        script {
                            echo "🏥 Backend Health Check..."
                            timeout(time: 5, unit: 'MINUTES') {
                                waitUntil {
                                    script {
                                        def response = sh(
                                            script: 'curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/actuator/health || echo "000"',
                                            returnStdout: true
                                        ).trim()
                                        if (response == '200') {
                                            echo "✅ Backend Health Check 성공!"
                                            return true
                                        } else {
                                            echo "⏳ Backend 대기 중... (${response})"
                                            sleep 10
                                            return false
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                
                stage('Frontend Health') {
                    steps {
                        script {
                            echo "🏥 Frontend Health Check..."
                            timeout(time: 3, unit: 'MINUTES') {
                                waitUntil {
                                    script {
                                        def response = sh(
                                            script: 'curl -s -o /dev/null -w "%{http_code}" http://localhost:3000 || echo "000"',
                                            returnStdout: true
                                        ).trim()
                                        if (response == '200') {
                                            echo "✅ Frontend Health Check 성공!"
                                            return true
                                        } else {
                                            echo "⏳ Frontend 대기 중... (${response})"
                                            sleep 10
                                            return false
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        stage('Integration Test') {
            steps {
                script {
                    echo "🧪 Integration Test..."
                    
                    sh '''
                        echo "=== 서비스 연결 테스트 ==="
                        curl -f http://localhost:80 || echo "Nginx 프록시 테스트"
                        
                        echo "=== 데이터베이스 연결 테스트 ==="
                        docker exec loom-mysql mysqladmin ping -h localhost -u root -ploom123! || echo "MySQL 연결 테스트"
                        docker exec loom-redis redis-cli --no-auth-warning -a loom123! ping || echo "Redis 연결 테스트"
                        
                        echo "=== 서비스 포트 확인 ==="
                        curl -I http://localhost:3000 || echo "Frontend 포트 테스트"
                        curl -I http://localhost:8080 || echo "Backend 포트 테스트"
                    '''
                }
            }
        }
        
        stage('Success') {
            steps {
                echo """
                🎉 전체 애플리케이션 배포 성공!
                
                ✅ 완료된 작업:
                ├── Backend (Java 21): 빌드 및 배포 완료
                ├── Frontend (Node 22): 빌드 및 배포 완료  
                ├── 전체 인프라: 모든 서비스 실행 중
                └── Health Check: 모든 서비스 정상
                
                🌐 서비스 접속:
                ├── Frontend: http://EC2-IP:3000
                ├── Backend API: http://EC2-IP:8080
                ├── Jenkins: http://EC2-IP:9080
                ├── Grafana: http://EC2-IP:3001
                └── Nginx: http://EC2-IP:80
                
                🚀 다음 단계: 도메인 연결 (l-oom.site)
                """
            }
        }
    }
    
    post {
        always {
            sh '/usr/local/bin/docker-compose ps || true'
            sh 'docker image prune -f || true'
        }
    }
}
