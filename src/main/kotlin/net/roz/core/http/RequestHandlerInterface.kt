package net.roz.core.http

interface RequestHandlerInterface {
    fun handle(request: RequestInterface): String
}