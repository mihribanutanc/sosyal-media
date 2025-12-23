pipeline {
    agent any

    environment {
        REGISTRY = "docker.io/mihribantanc"
        TAG_NAME = "${env.TAG_NAME ?: 'test-latest'}" // Tag yoksa test-latest kullan
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('List Workspace') {
            steps {
                sh 'ls -l'  // Workspace kontrolü
            }
        }

        stage('Check Tag') {
            steps {
                echo "Release tag detected: ${TAG_NAME}"
            }
        }

   stage('Build') {
       steps {
           // Gradlew'a çalıştırma izni ver
           sh 'chmod +x gradlew'
           // Build işlemini başlat
           sh './gradlew clean build -x test'
       }
   }

        stage('Docker Build') {
            steps {
                sh """
                docker build -t $REGISTRY/user-service:${TAG_NAME} ./user-service
                docker build -t $REGISTRY/auth-service:${TAG_NAME} ./auth-service
                docker build -t $REGISTRY/config-server:${TAG_NAME} ./config-server
                """
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'docker-hub',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh """
                    echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                    docker push $REGISTRY/user-service:${TAG_NAME}
                    docker push $REGISTRY/auth-service:${TAG_NAME}
                    docker push $REGISTRY/config-server:${TAG_NAME}
                    """
                }
            }
        }

        stage('Deploy to TEST') {
            steps {
                sshagent(['test-server-key']) {
                    sh """
                    ssh user@TEST_VM_IP "
                      docker compose -f docker-compose.test.yml pull &&
                      docker compose -f docker-compose.test.yml up -d
                    "
                    """
                }
            }
        }
    }
}
