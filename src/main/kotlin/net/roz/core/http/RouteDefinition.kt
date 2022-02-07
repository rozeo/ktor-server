package net.roz.core.http

import io.ktor.http.*
import kotlin.reflect.KClass

data class RouteDefinition<F: RequestHandlerInterface, S: MiddlewareInterface>(
    val method: HttpMethod,
    val uri: String,
    val handler: KClass<F>,
    val middlewares: List<KClass<S>> = listOf(),
) {
    companion object {
        fun <F: RequestHandlerInterface, S: MiddlewareInterface> prefix(
            prefix: String,
            internal: () -> Array<RouteDefinition<F, S>>
        ): Array<RouteDefinition<F, S>> {
            return group(prefix, listOf(), internal)
        }

        fun <F: RequestHandlerInterface, S: MiddlewareInterface> group(
            prefix: String,
            middlewares: List<KClass<S>>,
            internal: () -> Array<RouteDefinition<F, S>>
        ): Array<RouteDefinition<F, S>> {
            return internal.invoke().map {
                RouteDefinition(
                    it.method,
                    prefix + it.uri,
                    it.handler,
                    it.middlewares.plus(middlewares)
                )
            }.toTypedArray()
        }
    }
}