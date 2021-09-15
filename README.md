# Game-Round-Reconciliation
Reconciliation of game bets utilizing a stream based design / implementation.

### Deployment & Start Up
- mvn clean install package
- docker build -t mynifi .
- docker build -t game-round-reconciliation -f deployment/game-round-reconciliation/Dockerfile .
- cd deployment/
- docker-compose up
- Navigate to https://localhost:8443/nifi/ and upload deployment/MyTemplate.xml. Load the template, and start the data flow through Nifi.


### Exposed endpoints eg:
  - http://localhost/getBets
  - http://localhost/getBets/45049
  - http://localhost/getHourlyAggregates/2019-01-01/2022-01-01/e0020cf47626c6391154fea145cd7fcd
  - http://localhost/getHourlyAggregates/2019-01-01/2022-01-01/e0020cf47626c6391154fea145cd7fcd
