package net.roz.core.http

import io.ktor.http.*
import java.io.ByteArrayInputStream
import java.io.InputStream

class Response(length: Long, stream: InputStream): ResponseInterface {
    override val contentType: ContentType = ContentType.parse("text/plain")
    override val contentLength: Long = length
    override var status: HttpStatusCode = HttpStatusCode(200, "OK")
    override val headers: Headers = Headers.Empty
    override val body: InputStream = stream

    companion object {
        fun fromString(string: String): Response {
            return Response(string.length.toLong(), ByteArrayInputStream(string.toByteArray()))
        }
    }
}