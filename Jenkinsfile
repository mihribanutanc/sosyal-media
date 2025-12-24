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
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                    )]) {
                         sh '''
                          set -e
                          echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin

                          docker push ${USER_SERVICE_IMAGE}
                          docker push ${AUTH_SERVICE_IMAGE}
                          docker push ${CONFIG_SERVER_IMAGE}
                        '''

                }
            }
        }

    }
}
