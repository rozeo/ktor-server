package net.roz.application.http.handlers

import io.ktor.util.*
import net.roz.core.http.RequestHandlerInterface
import net.roz.core.http.RequestInterface

class HelloWorldHandler(): RequestHandlerInterface {
    override fun handle(request: RequestInterface): String {
        return "${request.method.toString()}: ${request.body}\r\n" +
                request.headers.toMap().map { it.key + ": " + it.value }.joinToString("\r\n")
    }
}