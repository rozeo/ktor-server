package net.roz.core.application

import io.ktor.application.*
import io.ktor.application.Application as KtorApplication
import io.ktor.http.*
import io.ktor.response.*
import net.roz.core.application.Application
import io.ktor.routing.Routing as KtorRouting
import io.ktor.routing.*
import net.roz.core.application.Application.Companion.container
import net.roz.core.http.MiddlewareInterface
import net.roz.core.http.RequestHandlerInterface
import net.roz.core.http.ResponseInterface
import net.roz.core.http.ktor.KtorRequestConnector
import net.roz.core.http.ktor.KtorResponseConvertor
import java.util.*
import kotlin.reflect.KClass

class Routing(private val ktorRouting: KtorRouting) {
    companion object {
        val requestConnector = container.get(KtorRequestConnector::class)
        val responseConvertor = container.get(KtorResponseConvertor::class)
    }

    private var middlewares: List<KClass<*>> = listOf()
    private var prefix: String = ""

    fun <T: MiddlewareInterface> withMiddlewares(addMiddlewares: List<KClass<T>>, stackCall: Routing.() -> Unit) {
        val _middlewares = middlewares
        middlewares = middlewares + addMiddlewares

        stackCall.invoke(this)

        middlewares = _middlewares
    }

    fun withPrefix(addPrefix: String, stackCall: Routing.() -> Unit) {
        val _prefix = prefix
        prefix = prefix + addPrefix

        stackCall.invoke(this)

        prefix = _prefix
    }

    fun <T: RequestHandlerInterface> add(
        httpMethod: HttpMethod,
        uri: String,
        handler: KClass<T>
    ) {
        val fullUri = prefix + uri

        // instantiate classes
        val handlerImplements = container.get(handler)
        var middlewareImplements: List<MiddlewareInterface> = listOf()

        middlewares
            .forEach {
                val middleware = container.get(it)
                if (middleware is MiddlewareInterface) {
                    middlewareImplements = middlewareImplements + middleware
                }
            }

        ktorRouting.apply {
            route(fullUri, httpMethod) {
                handle {
                    val request = requestConnector.convertRequest(context)
                    var response: Optional<ResponseInterface> = Optional.empty()

                    middlewareImplements.forEach {
                        val result = it.handle(request)
                        if (result.isPresent) {
                            response = Optional.of(result.get())
                            return@forEach
                        }
                    }

                    if (response.isEmpty) {
                        response = Optional.of(handlerImplements.handle(request))
                    }

                    call.respond(responseConvertor.convertResponse(response.get()))
            }
        }
        }
    }
}