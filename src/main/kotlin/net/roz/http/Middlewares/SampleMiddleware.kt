package net.roz.http.Middlewares

import net.roz.core.http.MiddlewareInterface
import net.roz.core.http.ResponseInterface
import java.util.Optional

class SampleMiddleware: MiddlewareInterface {
    override fun handle(): Optional<ResponseInterface> {
        return Optional.empty()
    }
}