pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                echo 'Building from Jenkinsfile...'
                git branch: 'main', url: 'https://github.com/barryismaila4/version-update.git'
                sh 'mvn clean compile -Dmaven.compiler.source=11 -Dmaven.compiler.target=11'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing...'
                sh 'mvn test -Dmaven.compiler.source=11 -Dmaven.compiler.target=11'
            }
        }
    }
}
