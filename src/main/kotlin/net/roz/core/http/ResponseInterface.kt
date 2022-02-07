package net.roz.core.http

import io.ktor.http.*
import java.io.InputStream

interface ResponseInterface {
    val contentType: ContentType
    val contentLength: Long
    val status: HttpStatusCode
    val headers: Headers

    val body: InputStream
}