package net.roz.http

import java.io.OutputStream

interface ResponseInterface {
    fun getBody(): OutputStream
}