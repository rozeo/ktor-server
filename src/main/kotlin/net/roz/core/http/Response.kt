package net.roz.core.http

import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import java.io.ByteArrayInputStream
import java.io.InputStream

class Response(length: Long, stream: InputStream): ResponseInterface {
    override var contentType: ContentType = ContentType.parse("text/plain")
    override var status: HttpStatusCode = HttpStatusCode(200, "OK")
    override val headers: HttpHeaders = HttpHeaders()

    override val contentLength: Long = length
    override val body: InputStream = stream

    companion object {
        fun fromString(string: String): Response {
            return Response(string.length.toLong(), ByteArrayInputStream(string.toByteArray()))
        }
    }
}