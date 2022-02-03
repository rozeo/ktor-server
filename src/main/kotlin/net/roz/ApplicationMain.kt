package net.roz

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import net.roz.application.Routing
import net.roz.di.Container

fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

fun Application.bootstrap() {
    val container = Container()

    Routing().forEach {
        routing {
            val controller = container.get(it.handler)
            when (it.method) {
                HttpMethod.Get -> get(it.uri) { call.respond(controller.handle()) }
                HttpMethod.Post -> get(it.uri) { call.respond(controller.handle()) }
                HttpMethod.Patch -> get(it.uri) { call.respond(controller.handle()) }
                HttpMethod.Put -> get(it.uri) { call.respond(controller.handle()) }
                HttpMethod.Delete -> get(it.uri) { call.respond(controller.handle()) }
            }
        }
    }
}