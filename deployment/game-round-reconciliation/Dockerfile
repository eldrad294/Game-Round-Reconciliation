FROM openjdk:11
LABEL maintainer="Gabriel"
LABEL name="Game-Round-Reconciliation"
COPY target/game-round-reconciliation-0.0.1-SNAPSHOT.jar service.jar
EXPOSE 80
# Run the application.
ENTRYPOINT java -jar  /service.jar