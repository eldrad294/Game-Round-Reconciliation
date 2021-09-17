# Game-Round-Reconciliation
Reconciliation of game bets utilizing a stream based design / implementation.

The java based service offers two alternatives to viewing aggregates:
1) MAIN - Consider this my final solution. Aggregates are performed on demand. Records are shipped through Kafka at realtime and upserted to postgres. Aggregates computed at microservice level.
2) EXPERIMENTAL - Alternative setup. Aggregates are performed at realtime. Records are shipped and aggregated through Kafka/Java at realtime, and upserted to postgres. Microservice fetches pre-computed aggregates.

## Setup

### DEPLOYMENT setup
- deployment/data/ - Contains the csv data for this demo.
- deployment/game-round-reconciliation - Contains the Dockerfile for the Spring service.
- deployment/nifi/ - Contains the Dockerfile and Nifi XML template. A custom image was written for NIFI so as to chmod the data files mounted as volumes.
- deployment/docker-compose.yml - Orchestration file.

### SRC setup
- src/main/java/com/gameroundreconciliation/controllers - Contains the REST microservice components of the service. Any API endpoints are located here.
- src/main/java/com/gameroundreconciliation/jpa - Contains any services / repository logic which interfaces the service with PostgresDB.
- src/main/java/com/gameroundreconciliation/kafka - Contains any consumer and configuration logic for the service to consume data from Kafka.

### Deployment & Start Up
- Move data files under deployment/data/
- mvn clean install package -DskipTests
- docker build -t mynifi -f deployment/nifi/Dockerfile .
- docker build -t game-round-reconciliation -f deployment/game-round-reconciliation/Dockerfile .
- cd deployment/
- docker-compose up 
  - (Don't run in detached mode, otherwise you will not be able to see the consumer logging)
- Navigate to https://localhost:8443/nifi/ 
  - Login with user: admin and password: ctsBtRBKHRAx69EqUghvvgEvjnaLjFEB
  - Upload deployment/MyTemplate.xml. 
  - Load the template
  - Enable Kafka Process
  - Start the data flow through Nifi.

### Verification

Running docker-compose should show the Java service consuming records and shipping these to Postgres in realtime.

### Exposed endpoints (port 80) eg:
  MAIN - http://localhost/getBets
  MAIN - http://localhost/getBets/45049
  MAIN - http://localhost/getHourlyAggregates/2019-01-01/2022-01-01/e0020cf47626c6391154fea145cd7fcd
  MAIN - http://localhost/getDailyAggregates/2019-01-01/2022-01-01/e0020cf47626c6391154fea145cd7fcd
  EXPERIMENTAL - http://localhost/getPreAggregates/2019-01-01/2022-01-01/e0020cf47626c6391154fea145cd7fcd
