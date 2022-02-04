package net.roz.core.http

import io.ktor.application.*
import io.ktor.request.*

class KtorRequestConnector {
    fun convert(request: ApplicationRequest): RequestInterface
    {
        return RequestImpl(
            request.httpMethod,
            request.headers,
            request.document()
        )
    }
}