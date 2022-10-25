package se.vitberget.aoc.kafka

import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*

fun Application.configureRouting() {
    routing {

        static("/") {
            staticBasePackage = "static"
            resource("index.html")
            defaultResource("index.html")
        }

        post("/") {
            println("POST")

            call.respond(HttpStatusCode.Created)
        }
    }
}