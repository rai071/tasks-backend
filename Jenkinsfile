pipeline {
    agent any
    stages {
        stage ('Build Backend') {
            steps {
                bat 'mvn clean package -DskipTests=true'
            }
        }
        stage ('Unit Tests') {
            steps {
                bat 'mvn test'
            }
        }
        stage ('Sonar Analysis') {
            environment {
                scannerHome=tool 'SONAR_SCANNER'
            }
            steps {
                withSonarQubeEnv('SONAR_LOCAL') {
                    bat "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=d28976c2cd756ba7fbcb5b5ebf37070dfd8d131f -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**src/test/**,**/model/**,**TaskBackendApplication.java** "
                }
            }
        }
        stage ('Quality Gate') {
            steps {
                sleep(5)
                timeout(time: 2, unit: 'MINUTES'){
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage ('Deploy') {
            steps {
                deploy adapters: [tomcat8(credentialsId: 'tomcat_deploy', path: '', url: 'http://localhost:8181')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
            }
        }
    }
}