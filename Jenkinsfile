pipeline {
    agent any

     tools {
            maven 'Maven 3'
        }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'develop', url: 'https://github.com/mohammed-addoumi/finsight-backend.git'
            }
        }

        stage('Compile & Build') {
            steps {
                script {
                    sh 'mvn clean compile'
                }
            }
        }

        stage('Package') {
            steps {
                script {
                    sh 'mvn package -DskipTests'
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline executed successfully!'
        }
        failure {
            echo 'Pipeline failed. Please check logs.'
        }
    }
}
