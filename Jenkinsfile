stage('build') {
    steps {
        script {
            try {
                sh 'mvn --version'
                sh 'echo "start to build" '
                sh 'mvn clean package'
            } catch (Exception e) {
                echo "Failed to build the Maven project: ${e.message}"
                currentBuild.result = 'FAILURE'
                error "Build failed"
            }
        }
    }
}
