pipeline {
    agent any

     tools {
            maven 'Maven 3'
            docker 'Docker'
        }

    environment {
            DOCKER_IMAGE = 'mohammedaddoumi/finsight-backend'
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

        stage('Build Docker Image') {
                    steps {
                        script {
                            sh "docker build -t ${DOCKER_IMAGE}:latest ."
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
