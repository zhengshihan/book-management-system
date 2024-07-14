pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                // Use Maven to build the project
                bat 'mvn clean package'
            }
        }
       
    }
}
