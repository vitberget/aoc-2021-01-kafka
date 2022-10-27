# AOC Kafka

Having fun with Kafka and multiple Gradle modules, solving [advent of code from 2021 day 1](https://adventofcode.com/2021/day/1).

Some say this can easily be solved with a [few lines of code](https://github.com/vitberget/advent-of-code-kotlin/blob/main/app/src/main/kotlin/se/vbgt/aoc/year2021/day1/Day1.kt). 

*I say no!*

Scrolling through the nearly endless 2000 lines of numbers, we can se that 
this is a case for some big data methodology, so let us solve this with Kafka!

## Set up Kafka server

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

## Run the apps

Start the apps:
* [puzzle-receiver](puzzle-receiver/src/main/kotlin/se/vitberget/aoc/kafka/PuzzleReceiver.kt)
  Which starts a webpage where you can paste in your personal puzzle. 
* [puzzle-splitter](puzzle-splitter/src/main/kotlin/se/vitberget/aoc/kafka/PuzzleSplitter.kt)
  Magic application that divides the entire puzzle into lines
* [puzzle-counter](puzzle-counter/src/main/kotlin/se/vitberget/aoc/kafka/PuzzleCounter.kt)
  Which starts a page which presents the results for part1 and/or part2

The puzzle solving apps are:
* [puzzle-compare-part1](puzzle-compare-part1/src/main/kotlin/se/vitberget/aoc/kafka/PuzzleComparePart1.kt)
* [puzzle-compare-part2](puzzle-compare-part2/src/main/kotlin/se/vitberget/aoc/kafka/PuzzleComparePart2.kt)
