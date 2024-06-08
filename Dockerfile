# Étape 1: Utiliser une image de base légère pour télécharger l'artefact
FROM openjdk:17-jdk-slim

# Définir les variables d'environnement
ARG ARTIFACT_URL=http://192.168.15.128:9003/repository/nexus-release/tn/esprit/DevOps_Project/1.0/DevOps_Project-1.0.jar
ARG ARTIFACT_NAME=DevOps_Project-1.0.jar

# Télécharger l'artefact directement depuis Nexus
RUN apt-get update && apt-get install -y wget && \
    wget ${ARTIFACT_URL} -O ${ARTIFACT_NAME}

# Exposer le port
EXPOSE 8082

COPY --from=build-image devops/src/main/resources/application.properties config/application.properties
# Définir la commande d'exécution de l'application
ENTRYPOINT ["java", "-jar", "DevOps_Project-1.0.jar","-Dspring.config.location=config"]
