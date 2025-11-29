pipeline {
    agent any

    tools {
        jdk 'jdk21'           // Nom exact de JDK 21 dans Jenkins
        maven 'Maven 3.6.3'   // Nom exact de Maven 3.6.3 dans Jenkins
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
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
                sh "mvn sonar:sonar -Dsonar.login=$SONAR_TOKEN"
            }
        }

        stage('Deploy') {
            environment {
                APP_NAME = "my-appj"
                JAR_FILE = "target/${APP_NAME}-1.0-SNAPSHOT.jar"
            }
            steps {
                // Arrêter l'ancienne instance si elle existe
                sh 'pkill -f ${APP_NAME} || true'

                // Lancer le nouveau .jar en arrière-plan
                sh "nohup java -jar ${JAR_FILE} > ${APP_NAME}.log 2>&1 &"

                echo "Application ${APP_NAME} déployée avec succès."
            }
        }
    }

    post {
        success {
            echo 'Pipeline exécutée avec succès !'
        }
        failure {
            echo 'Pipeline échouée !'
        }
    }
}
