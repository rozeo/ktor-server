package net.roz.application.http.middlewares

import io.ktor.client.utils.EmptyContent.status
import io.ktor.http.*
import net.roz.core.http.MiddlewareInterface
import net.roz.core.http.RequestInterface
import net.roz.core.http.Response
import net.roz.core.http.ResponseInterface
import java.util.Optional

class DoubleMiddleware: MiddlewareInterface {
    override suspend fun handle(request: RequestInterface): ResponseInterface? {
        val checkValue = request.queryParameters["abort_one"]

        return checkValue
            ?.map { it.toInt() }
            ?.takeIf { it.contains(1) }
            ?.let {
                Response.fromString("aborted by one!").apply {
                    status = HttpStatusCode.BadRequest
                }
            }
    }
}