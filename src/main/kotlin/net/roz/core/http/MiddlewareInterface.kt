package net.roz.core.http

import java.util.*

interface MiddlewareInterface {
    fun handle(): Optional<ResponseInterface>
}