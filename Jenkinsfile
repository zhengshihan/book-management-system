pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                
                echo 'Building...'
                sh 'mvn --version'
                sh 'mvn clean package'
            }
        }
        
    }
}
