
FROM openjdk:11-jdk-slim

ARG ARTIFACT_URL=http://localhost:9003/repository/nexus-release/tn/esprit/DevOps_Project/1.1/DevOps_Project-1.1.jar
ARG ARTIFACT_NAME=DevOps_Project-1.0.jar

RUN apt-get update &&  apt-get install -y wget && \
    wget ${ARTIFACT_URL} -O ${ARTIFACT_NAME}

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "DevOps_Project-1.1.jar","-Dspring.config.location=config"]