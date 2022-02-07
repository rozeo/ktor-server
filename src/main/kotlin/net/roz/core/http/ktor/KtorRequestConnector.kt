package net.roz.core.http.ktor

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.util.*
import net.roz.core.http.Request
import net.roz.core.http.RequestInterface

class KtorRequestConnector {
    suspend fun convertRequest(call: ApplicationCall): RequestInterface
    {
        val headers = call.request.headers.toMap()
        val queryParameters = call.request.queryParameters.toMap()

        return Request(
            call.request.httpVersion,
            call.request.httpMethod,
            call.request.uri,
            call.request.origin.scheme,
            call.request.host(),
            call.request.port(),
            call.request.path(),
            call.request.document(),
            call.request.origin.remoteHost,
            headers,
            queryParameters,
            if (receivable(headers)) call.receiveParameters().toMap() else mapOf()
        )
    }

    private fun receivable(headers: Map<String, List<String>>): Boolean
    {
        val header = headers["Content-Type"]

        return header?.contains("application/x-www-form-urlencoded") == true
            || header?.contains("multipart/form-data") == true
    }
}