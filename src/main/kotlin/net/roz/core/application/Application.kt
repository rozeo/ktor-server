package net.roz.core.application

import io.ktor.application.*
import io.ktor.application.Application as KtorApplication
import io.ktor.response.*
import io.ktor.routing.*
import net.roz.application.containerDefinitions
import net.roz.application.routingDefinitions
import net.roz.core.di.Container
import net.roz.core.http.*
import net.roz.core.http.ktor.KtorRequestConnector
import net.roz.core.http.ktor.KtorResponseConvertor
import java.util.Optional

class Application(private val ktorApplication: KtorApplication) {
    companion object {
        private val container = Container()
        private val requestConnector = container.get(KtorRequestConnector::class)
        private val responseConvertor = container.get(KtorResponseConvertor::class)
    }

    fun containerBinding(): Application {
        containerDefinitions(container)
        return this
    }

    fun routeBinding(): Application {
        routingDefinitions().forEach { definition ->
            ktorApplication.apply {
                routing {
                    routeHandling(this, definition)
                }
            }
        }

        return this
    }

    fun routeHandling(routing: Routing, definition: RouteDefinition<*, *>) {
        routing.apply {

            // instantiate classes
            val handler = container.get(definition.handler)
            val middlewares = definition.middlewares.map { container.get(it) }

            route(definition.uri, definition.method) {
                handle {

                    val request = requestConnector.convertRequest(context)
                    var response: Optional<ResponseInterface> = Optional.empty()

                    middlewares.forEach {
                        val result = it.handle(request)
                        if (result.isPresent) {
                            response = Optional.of(result.get())
                            return@forEach
                        }
                    }

                    if (response.isEmpty) {
                        response = Optional.of(handler.handle(request))
                    }

                    call.respond(responseConvertor.convertResponse(response.get()))
                }
            }
        }
    }
}


