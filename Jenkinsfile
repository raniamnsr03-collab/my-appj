pipeline {
    agent any

    tools {
        jdk 'jdk21'               // JDK configuré dans Jenkins
        maven 'Maven 3.6.3'       // Maven configuré dans Jenkins
    }

    stages {


        stage('Build WAR') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SAST - SonarQube') {
            environment {
                SONAR_TOKEN = credentials('sonar-token')
            }
            steps {
                sh '''
                mvn sonar:sonar \
                    -Dsonar.projectKey=my-appj \
                    -Dsonar.host.url=http://localhost:9000 \
                    -Dsonar.login=${SONAR_TOKEN}
                '''
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                sh '''
                echo "Déploiement WAR vers Tomcat..."
                sudo cp target/*.war /opt/tomcat/latest/webapps/
                sudo systemctl restart tomcat
                '''
            }
        }

    } // fin des stages

    post {
        success {
            echo 'Pipeline exécutée avec succès !'
        }
        failure {
            echo 'Pipeline échouée !'
        }
    }
}
