package net.roz.application

import io.ktor.http.*
import net.roz.core.http.RouteDefinition
import net.roz.application.http.handlers.HelloWorldHandler
import net.roz.application.http.middlewares.SampleMiddleware

fun routingDefinitions(): Array<RouteDefinition<*, *>> {
    return arrayOf(
        *RouteDefinition.prefix("/hoge") {
            arrayOf(
                *RouteDefinition.prefix("/fuga") {
                    arrayOf(
                        RouteDefinition(HttpMethod.Get, "/", HelloWorldHandler::class, listOf(SampleMiddleware::class))
                    )
                }
            )
        },
    )
}