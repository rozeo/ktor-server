package net.roz.application

import io.ktor.http.*
import net.roz.core.http.RouteDefinition
import net.roz.application.http.handlers.HelloWorldHandler
import net.roz.application.http.handlers.PostHandler
import net.roz.application.http.middlewares.SampleMiddleware
import net.roz.core.http.MiddlewareInterface

fun routingDefinitions(): Array<RouteDefinition<*, *>> {
    return arrayOf(
        RouteDefinition(HttpMethod.Get, "/", HelloWorldHandler::class, listOf(SampleMiddleware::class)),
        RouteDefinition<PostHandler, MiddlewareInterface>(HttpMethod.Post, "/", PostHandler::class),
    )
}