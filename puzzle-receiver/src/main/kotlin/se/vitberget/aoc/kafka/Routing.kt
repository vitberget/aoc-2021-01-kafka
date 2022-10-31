package se.vitberget.aoc.kafka

import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import se.vitberget.aoc.kafka.things.kafkaTo
import java.util.*

fun Application.configureRouting() {
    routing {
        static("/") {
            staticBasePackage = "static"
            resource("index.html")
            defaultResource("index.html")
        }

        post("/") {
            val puzzle = call.receiveParameters()["puzzle-data"].toString()
            val uuid = UUID.randomUUID().toString()
            kafkaTo("puzzle", uuid,  puzzle)
            call.respond(HttpStatusCode.Created, "Tack tack! $uuid")
        }
    }
}