pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                // Use Maven to build the project
                bat 'mvn clean package'
            }
        }
        stage('Deploy') {
            steps {
                script {
                    // Deploy to Tomcat
                    deploy adapters: [tomcat9(credentialsId: 'tomcat-credentials', path: '', url: 'http://localhost:9666')],
                           contextPath: '/', jar: 'C:/Users/zhengshihan/.jenkins/workspace/Bookstore_/target/demo-1-0.0.1-SNAPSHOT.jar'
                }
            }
        }
    }

    post {
        always {
            echo 'This will run after every build'
        }
        success {
            echo 'This will run only if the build succeeds'
        }
        failure {
            echo 'This will run only if the build fails'
        }
    }
}
