pipeline {
    agent any

    environment {
        REGISTRY = "docker.io/mihribantanc"
        TAG_NAME = "${env.TAG_NAME ?: 'test-latest'}"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('List Workspace') {
            steps {
                sh 'ls -l'
            }
        }

        stage('Check Tag') {
            steps {
                echo "Release tag detected: ${TAG_NAME}"
            }
        }

        stage('Build') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew clean build -x test'
            }
        }

        stage('Docker Build') {
            steps {
                sh """
                docker build -t $REGISTRY/user-service:${TAG_NAME} ./UserMicroservice
                docker build -t $REGISTRY/auth-service:${TAG_NAME} ./AuthMicroservice
                docker build -t $REGISTRY/config-server:${TAG_NAME} ./ConfigServer
                """
            }
        }

        stage('Deploy to TEST') {
            steps {
                sshagent(['test-server-key']) {
                    sh """
                    ssh user@TEST_VM_IP '
                      docker compose -f docker-compose.test.yml pull &&
                      docker compose -f docker-compose.test.yml up -d
                    '
                    """
                }
            }
        }

    }
}
