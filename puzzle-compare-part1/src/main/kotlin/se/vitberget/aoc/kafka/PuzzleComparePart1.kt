package se.vitberget.aoc.kafka


import org.apache.kafka.clients.consumer.ConsumerRecord
import se.vitberget.aoc.kafka.things.createTopic
import se.vitberget.aoc.kafka.things.kafkaFrom
import se.vitberget.aoc.kafka.things.kafkaTo
import java.util.concurrent.ConcurrentHashMap

fun main() {
    println("Creating topics")
    createTopic("lines")
    createTopic("count")

    println("Starting listening")

    kafkaFrom("lines", "part1") { part1(it) }
}

val memoryLeak = ConcurrentHashMap<String, Int>()

private fun part1(consumerRecord: ConsumerRecord<String, String>) {
    val (key, index) = consumerRecord.key().split(" ")
    println("got $key $index")

    val depth = consumerRecord.value().toInt()

    with(memoryLeak.put(key, depth)) {
        if (this != null && depth > this) {
            println("ping $depth>$this")
            kafkaTo("count", "$key-part1 $index", "1")
        } else {
            println("     $depth<$this")
        }
    }
}