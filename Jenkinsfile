pipeline {
    agent any
	 tools {
		maven 'MAVEN_LOCAL'
	}
    stages {
        stage ('Build Backend') {
            steps {
                sh "mvn clean package -DskipTests=true" 
            }
        }
        stage ('Unit Tests') {
            steps {
                sh "mvn test" 
            }
        }		 
        stage ('Sonar Analysis') {
            environment {
                scannerHome=tool 'SONAR_SCANNER'
            }
            steps {
                withSonarQubeEnv('SONAR_LOCAL') {
                    sh "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://10.1.1.25:9000 -Dsonar.login=34f0266b494d09187ad65c83b06d96d80feed389 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**src/test/**,**/model/**,**TaskBackendApplication.java**"
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
                deploy adapters: [tomcat8(credentialsId: 'tomcat_deploy', path: '', url: 'http://10.1.1.20:8080')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
            }
        }
    }
	post {
		always {
			junit 'target/surefire-reports/*.xml'
		}
	}
}
