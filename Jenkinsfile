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
                 sh "mvn -version"
                 sh "mvn clean package -DskipTests"
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
          stage('Fetch Artifact') {
             steps {
                 sh "wget http://192.168.15.128:9003/repository/nexus-release/tn/esprit/DevOps_Project/1.0/DevOps_Project-1.0.jar"
             }
          }
     }

     post {
         always {
             cleanWs()
         }
     }
 }
