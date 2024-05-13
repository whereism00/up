pipeline {
    agent any
    
    stages {
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