FROM openjdk:11-jdk-slim

WORKDIR /app

COPY target/DevOps_Project-1.1.jar /app/DevOps_Project-1.1.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app/DevOps_Project-1.0.jar"]
