package se.vitberget.aoc.kafka

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import se.vitberget.aoc.kafka.things.createTopic

fun main() {
    println("Creating topic")
    createTopic("puzzle", 1, 1)
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
