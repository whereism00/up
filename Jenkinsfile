pipeline {
    agent any
    
    stages {
        stage("Compile") {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage("Tests") {
            when {
                branch 'feature/*'
            }
            steps {
                sh 'mvn test'
            }
        }
        stage("Static analyse") {
            when {
                branch 'develop'
            }
            steps {
                sh 'mvn checkstyle:check'
            }
        }
        stage("Report") {
            when {
                branch 'feature/*'
            }
            steps {
                jacoco(
                    minimumInstructionCoverage: '50'
                )
            }
        }
        stage("Install") {
            steps {
                sh 'mvn install'
            }
        }
        stage("Publish") {
            steps {
                sh 'copy "MainModule\\target\\MainModule-1.0-SNAPSHOT-jar-with-dependencies.jar" "C:\\Users\\marie\\Desktop\\MainModule-1.0.jar"'
            }
        }
    }
}