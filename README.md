### Setup and run guide

- Install JAVA 17
- Install kafka

### Start kafka and create topic by following command [Ubuntu]
- zookeeper-server-start.sh config/zookeeper.properties
- kafka-server-start.sh config/server.properties
- kafka-topics.sh --create --topic order.created --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
- kafka-topics.sh --create --topic order.processed --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
- kafka-topics.sh --list --bootstrap-server localhost:9092

### Build and run the projects, for testing use postman or other client, or curl

