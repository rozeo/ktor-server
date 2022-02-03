package net.roz.http

import io.ktor.http.*
import kotlin.reflect.KClass

data class  RouteDefinition<F: RequestHandlerInterface, S: MiddlewareInterface>(
    val method: HttpMethod,
    val uri: String,
    val handler: KClass<F>,
    val middlewares: List<KClass<S>>,
)