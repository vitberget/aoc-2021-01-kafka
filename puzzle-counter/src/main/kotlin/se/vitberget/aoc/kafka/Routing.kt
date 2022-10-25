package se.vitberget.aoc.kafka

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respond(HttpStatusCode.OK,
                memoryLeak.entries
                    .joinToString(separator = "\n") { "${it.key} is ${it.value}" }
            )
        }
    }
}