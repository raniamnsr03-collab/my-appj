pipeline {
    agent any

 tools {
     jdk 'jdk21'
     maven 'Maven 3.6.3'
 }


    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                sh '''
                sudo cp target/*.war /opt/tomcat/latest/webapps/
                sudo systemctl restart tomcat
                '''
            }
        }
    }
}
