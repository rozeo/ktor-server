package net.roz.application

import io.ktor.application.*
import io.ktor.http.*
import net.roz.http.Handlers.HelloWorldHandler
import net.roz.http.Middlewares.SampleMiddleware
import net.roz.http.RouteDefinition

fun Application.Routing(): List<RouteDefinition<*, *>> {
    return listOf(
        RouteDefinition(HttpMethod.Get, "/", HelloWorldHandler::class, listOf(SampleMiddleware::class))
    )
}