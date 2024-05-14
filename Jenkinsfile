pipeline {
    agent any

    environment {
        M2_HOME = "/Users/marie/Downloads/apache-maven-3.9.6-src/apache-maven-3.9.6"
        PATH = "${M2_HOME}/bin:${PATH}"
    }

    tools {
	maven "mvn"
    }
    
    stages {
        stage("Checkout") {
		steps {
			checkout scmGit(branches: [[name: 'feature/4']],
		userRemoteConfigs: [[url: 'https://github.com/whereism00/up.git']])
        }
        stage("Compile") {
            steps {
                bat 'mvn clean compile'
            }
        }
        stage("Tests") {
            when {
                branch 'feature/*'
            }
            steps {
                bat 'mvn test'
            }
        }
        stage("Static analyse") {
            when {
                branch 'develop'
            }
            steps {
                bat 'mvn checkstyle:check'
            }
        }
        stage("Report") {
            when {
                branch 'feature/*'
            }
            steps {
                junit testResults: '**/surefire-reports/*.xml'
                jacoco(
                    minimumInstructionCoverage: '50'
                )
            }
        }
        stage("Install") {
            steps {
                bat 'mvn install'
            }
        }
        stage("Publish") {
            steps {
                bat 'copy "MainModule\\target\\MainModule-1.0-SNAPSHOT-jar-with-dependencies.jar" "C:\\Users\\marie\\Desktop\\MainModule-1.0.jar"'
            }
        }
    }
}