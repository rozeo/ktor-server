package net.roz.http

import java.util.Optional

interface MiddlewareInterface {
    fun handle(): Optional<ResponseInterface>
}