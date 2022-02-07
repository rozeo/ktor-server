package net.roz.application

import io.ktor.http.*
import net.roz.application.http.handlers.HelloWorldHandler
import net.roz.application.http.handlers.PostHandler
import net.roz.application.http.middlewares.SampleMiddleware
import net.roz.core.application.Routing

fun routingDefinitions(routing: Routing) {
    routing.apply {
        withMiddlewares(listOf(SampleMiddleware::class)) {
            add(HttpMethod.Get, "/", HelloWorldHandler::class)
            withPrefix("/hoge") {
                add(HttpMethod.Get, "/", HelloWorldHandler::class)
            }
        }

        add(HttpMethod.Post, "/", PostHandler::class)
    }
}