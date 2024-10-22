pipeline {
    agent any

    tools {
        // Specify the version of Maven and JDK you are using (if necessary)
        maven 'Maven 3.x'
        jdk 'Java 17'
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
                sh './mvnw clean install'
            }
        }

        // Run unit tests using JUnit
        stage('Test') {
            steps {
                sh './mvnw test'
            }
        }

        // Package the application (e.g., into a JAR or WAR)
        stage('Package') {
            steps {
                sh './mvnw package'
            }
        }

        // Deploy the application
        stage('Deploy') {
            steps {
                // Deploy to a staging/production server, such as Tomcat or Docker
                echo 'Deploying application...'
                // Add your deployment script here
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true
        }
        success {
            echo 'Build and deployment succeeded!'
        }
        failure {
            echo 'Build or deployment failed.'
        }
    }
}
