package net.roz.application.http.handlers

import net.roz.core.http.RequestHandlerInterface
import net.roz.core.http.RequestInterface
import net.roz.core.http.Response
import net.roz.core.http.ResponseInterface

class HelloWorldHandler(): RequestHandlerInterface {
    override suspend fun handle(request: RequestInterface): ResponseInterface {
        val string =  "${request.httpMethod}: ${request.queryParameters}\r\n" +
                request.headers.toMap().map { it.key + ": " + it.value.joinToString(" ") }.joinToString("\r\n")

        return Response.fromString(string)
    }
}