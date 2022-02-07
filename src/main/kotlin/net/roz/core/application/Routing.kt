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

    private var middlewares: MutableList<MiddlewareInterface> = mutableListOf()
    private var prefix: String = ""

    fun withMiddlewares(addMiddlewares: List<KClass<*>>, stackCall: Routing.() -> Unit) {
        val middlewareCache = middlewares

        addMiddlewares.forEach {
            val middleware = container.get(it)

            // filter interface
            if (middleware is MiddlewareInterface) {
                middlewares.add(middleware)
            }
        }

        stackCall.invoke(this)

        middlewares = middlewareCache
    }

    fun withPrefix(addPrefix: String, stackCall: Routing.() -> Unit) {
        val prefixCache = prefix
        prefix += addPrefix

        stackCall.invoke(this)

        prefix = prefixCache
    }

    fun <T: RequestHandlerInterface> add(
        httpMethod: HttpMethod,
        uri: String,
        handler: KClass<T>
    ) {
        val fullUri = prefix + uri

        // instantiate classes
        val handlerImplements = container.get(handler)
        val middlewareImplements = middlewares

        ktorRouting.apply {
            route(fullUri, httpMethod) {
                handle {
                    val request = requestConnector.convertRequest(context)
                    var response: Optional<ResponseInterface> = Optional.empty()

                    middlewareImplements.forEach {
                        val result = it.handle(request)
                        if (result != null) {
                            response = Optional.of(result)
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