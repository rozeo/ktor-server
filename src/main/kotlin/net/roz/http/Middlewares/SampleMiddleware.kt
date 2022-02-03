package net.roz.http.Middlewares

import io.ktor.http.content.*
import net.roz.http.MiddlewareInterface
import net.roz.http.ResponseInterface
import java.io.OutputStream
import java.util.Optional

class SampleMiddleware: MiddlewareInterface {
    override fun handle(): Optional<ResponseInterface> {
        return Optional.empty()
    }
}