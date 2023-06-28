pipeline {
    agent any
    tools {
        maven "M3"
        jdk "JDK"
    }
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Test'){
			steps{
				bat "mvn clean test"
			}
		}
		stage('Verify') {
		    steps{
		        bat 'mvn -DskipTests verify'
		    }
		}
        stage('Build') {
            steps {
                git url: 'https://github.com/bluetata/java-maven-junit5-example.git', branch: 'main'
                bat "mvn install -Dmaven.test.skip=true"
            }
        }
    }
    post {
        always {
          junit(
            allowEmptyResults: true,
            testResults: '**/target/surefire-reports/*.xml'
          )
        }
    }
}
