pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                
                echo 'Building...'
                bat 'mvn --version'
                bat 'mvn clean package'
            }
        }
        
    }
}
