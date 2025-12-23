pipeline {
    agent any

    stages {

        stage('Build') {
            steps {
                echo 'Gradle build başlıyor'
                sh 'chmod +x gradlew'
                sh './gradlew clean build'
            }
        }

        stage('Docker Compose Up') {
            steps {
                echo 'Docker servisleri ayağa kaldırılıyor'
                sh 'docker compose up -d'
            }
        }
    }
}