pipeline {
    agent any
    tools{
        maven 'Maven3'
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
                    bat 'docker build -t rohitsinha025/selenium-docker-two -f ./Dockerfile .'
                }
            }
        }
        stage('Push image to Hub'){
            steps{
                script{
                    withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                        bat 'docker login -u rohitsinha025@gmail.com -p Hanuman@1209'
                        }
                        bat 'docker push rohitsinha025/selenium-docker-two'
                }
            }
        }
        stage('Run image file and go to shell mode'){
                    steps{
                        script{
                        bat 'docker run --entrypoint=/bin/sh rohitsinha025/selenium-docker-two'
                    }
                 }
        }
        stage('Execute testng.xml file'){
                      steps{
                          script{
                          bat 'java -cp selenium-docker.jar:selenium-docker-tests.jar:libs/*'
                      }
                 }
        }
    }
}