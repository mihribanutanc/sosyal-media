pipeline {
    agent any

    environment {
        GRADLE_HOME = './gradlew'
        DOCKER_IMAGE_PREFIX = 'mihribantanc'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/mihribanutanc/sosyal-media.git'
            }
        }

        stage('Build') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew clean build'
            }
        }

        stage('Docker Build') {
            steps {
                // Örnek: Her microservice için image
                sh 'docker build -t $DOCKER_IMAGE_PREFIX/authmicroservice:latest AuthMicroservice/'
                sh 'docker build -t $DOCKER_IMAGE_PREFIX/configserver:latest ConfigServer/'
                sh 'docker build -t $DOCKER_IMAGE_PREFIX/usermicroservice:latest UserMicroservice/'
            }
        }

        stage('Docker Compose Up') {
            steps {
                sh 'docker-compose up -d'
            }
        }
    }

    post {
        success {
            echo 'Pipeline başarılı 🎉'
        }
        failure {
            echo 'Pipeline başarısız ❌'
        }
    }
}