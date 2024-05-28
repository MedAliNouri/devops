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
     }

     post {
         always {
             cleanWs()
         }
     }
 }
