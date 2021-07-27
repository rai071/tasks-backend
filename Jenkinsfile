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
            enviroment {
                scannerHome=tool 'SONNAR_SCANNER'
            }
            steps {
                withSonarQubeEnv('SONNAR_LOCAL') {
                    bat "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=d28976c2cd756ba7fbcb5b5ebf37070dfd8d131f -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**src/test/**,**/model/**,**TaskBackendApplication.java** "
                }
            }
        }
        stage ('Quality Gate') {
            steps {
                timemout(time: 1, unit: 'MINUTES')
                waitForQaulityGate abortPipeline:true
            }
        }
    }
}