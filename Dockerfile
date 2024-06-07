FROM openjdk:17-jdk-alpine
EXPOSE 9089
COPY target/ski-app.jar ski-app.jar
ENTRYPOINT [¨java",¨-jar",¨/ski-app.jar"]
