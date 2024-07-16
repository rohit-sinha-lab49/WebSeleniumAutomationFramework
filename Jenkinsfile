pipeline {
    agent any
    tools{
        maven 'Maven3'
    }
    environment {
            //DOCKER_HUB_CREDENTIALS = credentials([string(credentialsId: 'rohitsinha025@gmail.com', variable: 'Hanuman@1209')]) // Jenkins credentials ID
            IMAGE_NAME = 'rohitsinha025/selenium-docker-again'
            DOCKER_COMPOSE_FILE = 'docker-compose.yaml'
        }
    stages{
        stage('Build Maven'){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/rohit-sinha-lab49/WebSeleniumAutomationFramework']])
                bat 'mvn clean package -DskipTests'
            }
        }
        stage('Build docker image'){
            steps{
                script{
                    bat 'docker build -t rohitsinha025/selenium-docker-again -f ./Dockerfile .'
                }
            }
        }
         stage('Push image to Hub'){
            steps{
                script{
                    withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                        bat 'docker login -u rohitsinha025@gmail.com -p Hanuman@1209'
                        }
                        bat 'docker push rohitsinha025/selenium-docker-again'
                   }
               }
          }
        stage('Run Docker Compose file') {
                    steps {
                        script {
                            // Ensure docker-compose file is updated with the correct image tag
                            withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                            bat 'docker login -u rohitsinha025@gmail.com -p Hanuman@1209'
                            }
                            bat 'docker-compose up'
                        }
                    }
        }
        /* stage('Run image file and go to shell mode'){
                    steps{
                        script{
                        bat 'docker run --rm rohitsinha025/selenium-docker-again'
                    }
                 }
        } */
        /* stage('Execute testng.xml file'){
                      steps{
                          script{
                          bat 'java -cp selenium-docker.jar:selenium-docker-tests.jar:libs *//*  *//*'
                      }
                 }
        }  */
    }

     post {
            always {
                // Clean up
                script {
                withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                                            bat 'docker login -u rohitsinha025@gmail.com -p Hanuman@1209'
                                            }
                    bat 'docker-compose down'
                }
            }
        }
}