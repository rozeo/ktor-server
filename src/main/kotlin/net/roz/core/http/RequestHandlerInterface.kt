package net.roz.core.http

interface RequestHandlerInterface {
    suspend fun handle(request: RequestInterface): ResponseInterface
}