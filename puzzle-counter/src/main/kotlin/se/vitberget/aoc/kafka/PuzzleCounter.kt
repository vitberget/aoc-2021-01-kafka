package se.vitberget.aoc.kafka

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.apache.kafka.clients.consumer.ConsumerRecord
import se.vitberget.aoc.kafka.things.createTopic
import se.vitberget.aoc.kafka.things.kafkaFrom


fun main() {
    println("Creating topics")
    createTopic("count", 1, 1)

    val port = 8012
    println("PuzzleReceiver port: $port http://localhost:$port/")

    embeddedServer(
        Netty,
        port = port,
        host = "0.0.0.0",
        watchPaths = listOf("classes")
    ) {
        configureRouting()
    }.start(wait = false)

    kafkaFrom("count", "counter") { counter(it) }
}

fun counter(consumerRecord: ConsumerRecord<String, String>) {
    val (key, index) = consumerRecord.key().split(" ")
    println("got $key $index")

    memoryLeak.compute(key) { _: String, prev: Int? ->
        if (prev == null)
            1
        else
            prev + 1
    }
}
