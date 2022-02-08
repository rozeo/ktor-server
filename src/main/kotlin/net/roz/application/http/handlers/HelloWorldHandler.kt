package net.roz.application.http.handlers

import io.ktor.client.utils.*
import io.ktor.http.HttpHeaders
import net.roz.core.http.RequestHandlerInterface
import net.roz.core.http.RequestInterface
import net.roz.core.http.Response
import net.roz.core.http.ResponseInterface
import java.util.Date

class HelloWorldHandler(): RequestHandlerInterface {
    override suspend fun handle(request: RequestInterface): ResponseInterface {
        val string =  "${request.httpMethod}: ${request.queryParameters}\r\n" +
                request.headers.toMap().map { it.key + ": " + it.value.joinToString(" ") }.joinToString("\r\n")

        return Response.fromString(string).apply {
            headers["hoge-header"] = (1).toString()
        }
    }
}