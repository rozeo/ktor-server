package net.roz.core.http.ktor

import io.ktor.http.content.*
import net.roz.core.http.ResponseInterface

class KtorResponseConvertor {
    fun convertResponse(response: ResponseInterface): OutgoingContent {
        return KtorResponse(response)
    }
}