# AOC Kafka

Having fun with Kafka and multiple Gradle modules.

## Game plan, apps

### complete

1. Page to enter puzzle
2. puzzle -> lines
3. compare numbers part1 -> send if increase
4. sum increases, show on webpage

### later

5. compare numbers part2 -> send if increase

## Get ready

<https://kafka.apache.org/quickstart>

1. Download and extract
   ```sh
   wget https://downloads.apache.org/kafka/3.3.1/kafka_2.13-3.3.1.tgz
   tar -xzf kafka_2.13-3.3.1.tgz
   cd kafka_2.13-3.3.1
   ```
2. Start zookeeper:
   ```sh
   bin/zookeeper-server-start.sh config/zookeeper.properties
   ```
3. Start kafka server (in antoher terminal):
   ```sh
   bin/kafka-server-start.sh config/server.properties
   ```
