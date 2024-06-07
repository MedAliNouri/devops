FROM openjdk:17-jdk-alpine
EXPOSE 8082
COPY target/ski-app.jar ski-app.jar
ENTRYPOINT [ "java", "-jar", "/ski-app.jar" ]
