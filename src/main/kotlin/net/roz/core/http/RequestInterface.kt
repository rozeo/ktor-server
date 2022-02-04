package net.roz.core.http

import io.ktor.http.*

interface RequestInterface {
    val body: String
    val headers: Headers
    val method: HttpMethod
}