pipeline {
    agent any
    tools{
        maven 'Maven3'
    }
    stages{
        stage('Build Maven'){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/rohit-sinha-lab49/WebSeleniumAutomationFramework']])
                bat 'mvn clean install'
            }
        }
        stage('Build docker image'){
            steps{
                script{
                    bat 'docker build -t rohitsinha025/devops-integration .'
                }
            }
        }
        stage('Push image to Hub'){
            steps{
                script{
                    withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                        bat 'docker login -u rohitsinha025@gmail.com -p Hanuman@1209'
                        }
                        bat 'docker push rohitsinha025/devops-integration'
                }
            }
        }
    }
}