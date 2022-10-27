package se.vitberget.aoc.kafka

import org.apache.kafka.clients.consumer.ConsumerRecord
import se.vitberget.aoc.kafka.things.createTopics
import se.vitberget.aoc.kafka.things.kafkaFrom
import se.vitberget.aoc.kafka.things.kafkaTo
import java.util.concurrent.ConcurrentHashMap

fun main() {
    println("Creating topics")
    createTopics("lines", "count")

    println("Starting listening")

    kafkaFrom("lines", "part2") { part2(it) }
}

data class Quad<out A, out B, out C, out D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
)

val memoryLeak = ConcurrentHashMap<String, Quad<Int?, Int?, Int?, Int?>>()

private fun part2(consumerRecord: ConsumerRecord<String, String>) {
    val (key, index) = consumerRecord.key().split(" ")
    println("got $key $index")

    val depth = consumerRecord.value().toInt()

    with(doCompute(key, depth)) {
        if (this != null && depth > this) {
            println("ping $depth > $this")
            kafkaTo("count", "$key-part2 $index", "1")
        } else {
            println("     $depth ! $this")
        }
    }
}

fun doCompute(key: String, depth: Int): Int? =
    memoryLeak.compute(key) { _, oldTrips -> updateTriple(oldTrips, depth) }?.first

private fun updateTriple(
    oldTrips: Quad<Int?, Int?, Int?, Int?>?,
    depth: Int
) = if (oldTrips == null)
    Quad(null, null, null, depth)
else
    Quad(oldTrips.second, oldTrips.third, oldTrips.fourth, depth)