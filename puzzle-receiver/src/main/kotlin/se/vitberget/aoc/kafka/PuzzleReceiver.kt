package se.vitberget.aoc.kafka

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import se.vitberget.aoc.kafka.things.createTopics

fun main() {
    println("Creating topic")
    createTopics("puzzle")
    val port = 8011
    println("PuzzleReceiver port: $port http://localhost:$port/")

    embeddedServer(
        Netty,
        port = port,
        host = "0.0.0.0",
        watchPaths = listOf("classes")
    ) {
        configureRouting()
    }.start(wait = true)
}
