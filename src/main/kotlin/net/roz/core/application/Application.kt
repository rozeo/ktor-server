package net.roz.core.application

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.application.Application as KtorApplication
import io.ktor.response.*
import io.ktor.routing.*
import net.roz.application.containerDefinitions
import net.roz.application.routingDefinitions
import net.roz.core.di.Container
import net.roz.core.http.RequestImpl
import net.roz.application.routingDefinitions
import net.roz.core.http.KtorRequestConnector

class Application(private val ktorApplication: KtorApplication) {
    companion object {
        private val container = Container()
    }

    fun containerBinding(): Application {
        containerDefinitions(container)
        return this
    }

    fun routeBinding(): Application {
        routingDefinitions().forEach {
            ktorApplication.apply {
                routing {
                    val handler = it.handler
                    route(it.uri, it.method) {
                        handle {
                            call.respond(container.get(handler).handle(container.get(KtorRequestConnector::class).convert(this.context.request)))
                        }
                    }
                }
            }
        }

        return this
    }
}


