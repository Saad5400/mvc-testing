pipeline {
    agent any

    environment {
        FLY_API_TOKEN = credentials('FLY_API_TOKEN')
    }

    stages {
        // Checkout the code from the repository
        stage('Checkout') {
            steps {
                git 'https://github.com/Saad5400/mvc-testing.git'
            }
        }

        // Build the project using Maven Wrapper
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        // Run unit tests using JUnit
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        // Deploy the application to Fly.io
        stage('Deploy') {
            steps {
                // install the Fly CLI
                sh 'curl -L https://fly.io/install.sh | sh'
                // deploy the application
                sh '/var/lib/jenkins/.fly/bin/flyctl deploy'
            }
        }
    }

    post {
        success {
            echo 'Build and deployment succeeded!'
        }
        failure {
            echo 'Build or deployment failed.'
        }
    }
}
