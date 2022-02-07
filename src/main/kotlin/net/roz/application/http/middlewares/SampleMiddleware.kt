package net.roz.application.http.middlewares

import io.ktor.http.*
import net.roz.core.http.MiddlewareInterface
import net.roz.core.http.RequestInterface
import net.roz.core.http.Response
import net.roz.core.http.ResponseInterface
import java.util.Optional

class SampleMiddleware: MiddlewareInterface {
    override suspend fun handle(request: RequestInterface): Optional<ResponseInterface> {
        if (request.queryParameters.containsKey("abort")) {
            return Optional.of(
                Response.fromString("aborted!").apply {
                    status = HttpStatusCode.BadRequest
                })
        }

        return Optional.empty()
    }
}