pipeline {
    agent any

    tools {
        jdk 'jdk21'               // JDK configuré dans Jenkins
        maven 'Maven 3.6.3'       // Maven configuré dans Jenkins
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

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
                echo "Déploiement du WAR dans Tomcat..."

                # Copier le WAR dans le dossier webapps de Tomcat
                sudo cp target/my-appj.war /opt/tomcat/latest/webapps/

                # Redémarrer Tomcat pour appliquer le déploiement
                sudo systemctl restart tomcat

                echo "Déploiement terminé avec succès !"
                '''
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
