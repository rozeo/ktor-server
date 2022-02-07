package net.roz.application.http.handlers

import net.roz.core.http.RequestHandlerInterface
import net.roz.core.http.RequestInterface
import net.roz.core.http.Response
import net.roz.core.http.ResponseInterface

class PostHandler: RequestHandlerInterface {
    override suspend fun handle(request: RequestInterface): ResponseInterface {
        return Response.fromString("posted! ${request.receiveParameters}")
    }
}