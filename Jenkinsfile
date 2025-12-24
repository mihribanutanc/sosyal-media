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

        stage('Build (Gradle)') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew clean build -x test'
            }
        }

        /* ================= DOCKER BUILD ================= */

        stage('Docker Build - User Service') {
            steps {
                sh "docker build -t $REGISTRY/user-service:${TAG_NAME} ./UserMicroservice"
            }
        }

        stage('Docker Build - Auth Service') {
            steps {
                sh "docker build -t $REGISTRY/auth-service:${TAG_NAME} ./AuthMicroservice"
            }
        }

        stage('Docker Build - Config Server') {
            steps {
                sh "docker build -t $REGISTRY/config-server:${TAG_NAME} ./ConfigServer"
            }
        }

        /* ================= DOCKER PUSH ================= */

        stage('Docker Login') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh 'echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin'
                }
            }
        }

        stage('Docker Push - User Service') {
            steps {
                sh "docker push $REGISTRY/user-service:${TAG_NAME}"
            }
        }

        stage('Docker Push - Auth Service') {
            steps {
                sh "docker push $REGISTRY/auth-service:${TAG_NAME}"
            }
        }

        stage('Docker Push - Config Server') {
            steps {
                sh "docker push $REGISTRY/config-server:${TAG_NAME}"
            }
        }
    }
}
