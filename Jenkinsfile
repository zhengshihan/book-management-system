pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                // 使用 Maven 构建项目
                bat 'mvn clean package'
            }
        }
        stage('Deploy') {
            steps {
                script {
                    // 部署到 Tomcat
                    deploy adapters: [tomcat9(credentialsId: 'tomcat-credentials', path: '', url: 'http://localhost:9666')],
                           contextPath: '/', war: '**/target/*.war'
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
