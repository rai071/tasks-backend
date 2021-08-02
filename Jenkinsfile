pipeline {
    agent any
	 tools {
		maven 'MAVEN_LOCAL'
	}
    stages {
        stage ('Build Backend') {
            steps {
                sh ''' 
					mvn clean package -DskipTests=true
				''' 
            }
        }
        stage ('Unit Tests') {
            steps {
                sh ''' 
					mvn test
				''' 
            }
        }
        stage ('Sonar Analysis') {
            environment {
                scannerHome=tool 'SONAR_SCANNER'
            }
            steps {
                withSonarQubeEnv('SONAR_LOCAL') {
                    sh ''' 
						${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://10.1.1.25:9000 -Dsonar.login=357ddd313d48bf350bc7da3966e8d03bbf8ff1c6 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**src/test/**,**/model/**,**TaskBackendApplication.java**
					''' 
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
    }
}
