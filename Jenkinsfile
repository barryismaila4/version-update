pipeline {
    agent any
    tools {
        jdk 'jdk21'
        maven 'maven3'
    }
    environment {
        SONAR_TOKEN = credentials('sonar-token')
    }
    stages {
        stage('Build') {
            steps {
                echo 'Building with Java 21...'
                git branch: 'main', url: 'https://github.com/barryismaila4/version-update.git'
                sh 'mvn clean compile -DskipTests=true'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                echo 'Analyzing code quality with SonarQube...'
                sh 'mvn sonar:sonar -Dsonar.projectKey=version-update -Dsonar.host.url=http://localhost:9000 -Dsonar.login=$SONAR_TOKEN -DskipTests=true'
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
