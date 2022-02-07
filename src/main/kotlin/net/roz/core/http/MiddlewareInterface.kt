package net.roz.core.http

import java.util.*

interface MiddlewareInterface {
    suspend fun handle(request: RequestInterface): Optional<ResponseInterface>
}