pipeline {
    agent any

    stages {
        // Checkout the code from the repository
        stage('Checkout') {
            steps {
                git 'https://github.com/Saad5400/mvc-testing.git'
            }
        }

        // Build the project and run the tests
        stage('Build') {
            steps {
                sh './mvnw clean test --no-transfer-progress'
            }
        }

        // Package the application into a Docker image
        stage('Package Docker Image') {
            steps {
                script {
                    sh 'docker build -t mvc-image .'
                }
            }
        }

        // Deploy the Docker container
        stage('Deploy Docker Container') {
            steps {
                script {
                    sh 'docker stop spring-boot-app || true && docker rm spring-boot-app || true'
                    sh 'docker run -d --name spring-boot-app -p 8081:8080 mvc-image'
                }
            }
        }
    }

    post {
        success {
            script {
                // Get the committer's name
                def committer = sh(script: "git log -1 --pretty=format:'%an'", returnStdout: true).trim()

                // Send the success email with committer's name only
                mail to: 'bilal.adila13@gmail.com',
                     subject: "SUCCESS: Build and Deployment Succeeded",
                     body: """Good news! The Jenkins pipeline executed successfully and your project was deployed.

Committer: ${committer}
"""
            }
            echo 'Build and deployment succeeded!'
        }
        failure {
            script {
                // Get the committer's name
                def committer = sh(script: "git log -1 --pretty=format:'%an'", returnStdout: true).trim()

                // Send the failure email with committer's name only
                mail to: 'bilal.adila13@gmail.com',
                     subject: "FAILURE: Build or Deployment Failed",
                     body: """Unfortunately, the Jenkins pipeline failed. Please check the console output for more details.

Committer: ${committer}
"""
            }
            echo 'Build or deployment failed.'
        }
    }
}
