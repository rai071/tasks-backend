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
                    sh "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://10.1.1.25:9000 -Dsonar.login=019dd91412b1a4146872cdf64f779f27f1272d3b -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**src/test/**,**/model/**,**TaskBackendApplication.java**"
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
				script {
					sshPublisher(publishers: [sshPublisherDesc(configName: 'intradev', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: 'ls', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '/BackEndPipeline/${BUILD_NUMBER}', remoteDirectorySDF: false, removePrefix: '', sourceFiles: 'Dockerfile'), sshTransfer(cleanRemote: false, excludes: '', execCommand: 'cd /app/BackEndPipeline/${BUILD_NUMBER};docker stop tomcat-app;docker rm tomcat-app;docker rmi teste;docker build -t teste .;docker run -d -p 8003:8080 --name tomcat-app teste;', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '/FullTestPipeline/${BUILD_NUMBER}', remoteDirectorySDF: false, removePrefix: 'target', sourceFiles: 'target/tasks-backend.war')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
				}
            }
        }
    }
	post {
		always {
			junit 'target/surefire-reports/*.xml'
		}
	}
}
