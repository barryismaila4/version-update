pipeline {
    agent any
    tools {
        jdk 'jdk21'
        maven 'maven3'
    }
    stages {
        stage('Build') {
            steps {
                echo 'Building with Java 21...'
                git branch: 'main', url: 'https://github.com/barryismaila4/version-update.git'
                sh 'mvn clean compile -DskipTests=true'
            }
        }
        stage('Test') {
            steps {
                echo 'Tests skipped (MySQL not available in Jenkins environment)'
                sh 'echo "Tests would run here with MySQL available"'
            }
        }
        stage('Package') {
            steps {
                echo 'Creating package...'
                sh 'mvn package -DskipTests=true'
            }
        }
    }
}
