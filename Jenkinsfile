 pipeline {
     agent any

     stages {
         stage('Cloning project') {

             steps {
                echo "Getting Project from Git sss";
                 git branch: "mohamed-ali-nouri",
                     url: "https://github.com/MedAliNouri/devops.git";
             }
         }



         stage("Build") {
             steps {
                 sh "mvn clean package -DskipTests"
             }
         }
          stage("Test") {
              steps {
                  sh "mvn test"
              }
          }
         stage('mvn sonarqube') {
             steps {
                 withSonarQubeEnv('sonar') {
                     sh 'mvn sonar:sonar'
                     }
                 }
             }
         stage('deploy') {
             steps {
               withSonarQubeEnv('sonar') {
                 sh 'mvn clean deploy'
               }
             }
         }
         stage('Build Docker Image') {
             steps {
                 script {
                     sh "docker build -t medalinouri/devops_project:latest ."
                 }
             }
         }
         stage('Verify Docker Image') {
             steps {
                 script {
                     sh "docker images" // Verify that the image exists
                 }
             }
         }

         stage('Push Docker Image to Docker Hub') {
             steps {
                  script {
                      withCredentials([string(credentialsId: 'DOCKER_HUB_CREDENTIALS', variable: 'dockerhubpwd')]) {
                        sh "docker login -u medalinouri -p ${dockerhubpwd}"
                         sh "docker push medalinouri/devops_project:latest"
                         }
                 }
             }
         }
     }

     post {
         always {
             cleanWs()

         }

     }
 }
