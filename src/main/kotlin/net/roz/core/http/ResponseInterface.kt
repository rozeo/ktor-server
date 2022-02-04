package net.roz.core.http

import java.io.OutputStream

interface ResponseInterface {
    fun getBody(): OutputStream
}