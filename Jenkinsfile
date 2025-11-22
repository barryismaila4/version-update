pipeline {
    agent any
    tools {
        jdk 'jdk21'  // Utilise le JDK qu'on vient de configurer
        maven 'maven3'
    }
    stages {
        stage('Build') {
            steps {
                echo 'Building with Java 21...'
                git branch: 'main', url: 'https://github.com/barryismaila4/version-update.git'
                sh 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing...'
                sh 'mvn test'
            }
        }
    }
}
