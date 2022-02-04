package net.roz.core.http

import io.ktor.http.*

class RequestImpl(
    override val method: HttpMethod,
    override val headers: Headers,
    override val body: String
): RequestInterface {
}