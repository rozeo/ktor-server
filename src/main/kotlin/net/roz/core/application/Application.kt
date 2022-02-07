package net.roz.core.application


import io.ktor.routing.*
import io.ktor.application.Application as KtorApplication
import io.ktor.routing.Routing as KtorRouting
import net.roz.application.containerDefinitions
import net.roz.application.routingDefinitions
import net.roz.core.di.Container
import net.roz.core.http.*

class Application(private val ktorApplication: KtorApplication) {
    private val routing: Routing = Routing(ktorApplication.routing{})

    companion object {
        val container = Container()
    }

    fun containerBinding(): Application {
        containerDefinitions(container)
        return this
    }

    fun routeBinding(): Application {
        routingDefinitions(routing)
        return this
    }

    fun routeHandling(routing: KtorRouting, definition: RouteDefinition<*, *>) {
        routing.apply {

            // instantiate classes
            val handler = container.get(definition.handler)
            val middlewares = definition.middlewares.map { container.get(it) }

            route(definition.uri, definition.method) {
                handle {
                }
            }
        }
    }
}


