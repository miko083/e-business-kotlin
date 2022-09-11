package com.example.routes

import com.example.models.Console
import com.example.consoleStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.consoleRouting() {
    route("/console") {
        get {
            if (consoleStorage.isNotEmpty()){
                call.respond(consoleStorage)
            } else {
                call.respondText("No consoles found", status = HttpStatusCode.OK)
            }
        }
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val console =
                consoleStorage.find { it.id == id } ?: return@get call.respondText(
                    "No console with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(console)

        }
        post {
            val console = call.receive<Console>()
            consoleStorage.add(console)
            call.respondText("Console stored correctly", status = HttpStatusCode.Created)
        }
        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (consoleStorage.removeIf { it.id == id }) {
                call.respondText("Console removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }

        }
    }
}