FROM openjdk:17-jdk-slim

ARG ARTIFACT_URL=http://192.168.15.128:9003/repository/nexus-release/tn/esprit/DevOps_Project/1.0/DevOps_Project-1.0.jar
ARG ARTIFACT_NAME=DevOps_Project-1.0.jar

RUN apt-get update &&  apt-get install -y wget && \
    wget ${ARTIFACT_URL} -O ${ARTIFACT_NAME}

EXPOSE 8082

COPY src/main/resources/application.properties config/application.properties
ENTRYPOINT ["java", "-jar", "DevOps_Project-1.0.jar","-Dspring.config.location=config"]
