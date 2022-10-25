package se.vitberget.aoc.kafka

import org.apache.kafka.clients.consumer.ConsumerRecord
import se.vitberget.aoc.kafka.things.createTopic
import se.vitberget.aoc.kafka.things.kafkaFrom
import se.vitberget.aoc.kafka.things.kafkaTo

fun main() {
    println("Creating topics")
    createTopic("puzzle", 1, 1)
    createTopic("lines", 1, 1)

    println("Starting listening")

    kafkaFrom("puzzle", "splitter2") { splitterFun(it) }
}

private fun splitterFun(consumerRecord: ConsumerRecord<String, String>) {
    val key = consumerRecord.key()
    println("got $key")
    consumerRecord.value()
        .lines()
        .filter { it.isNotBlank() }
        .forEachIndexed { i, line -> kafkaTo("lines", "$key $i", line) }
}