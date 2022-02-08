package net.roz.core.http

import io.ktor.client.utils.EmptyContent.headers
import io.ktor.http.Headers

class HttpHeaders: Headers {
    override val caseInsensitiveName: Boolean = false

    private val headers: MutableMap<String, MutableList<String>> = mutableMapOf()

    private fun nameConvert(name: String): String =
        name
            .split("-")
            .joinToString("-") {
                it.lowercase()
                    .replaceFirstChar { first -> first.uppercase() }
            }

    operator fun set(name: String, value: String) {
        val upperCaseName = nameConvert(name)

        headers[upperCaseName]?.add(value) ?: headers.set(upperCaseName, mutableListOf(value))
    }

    override fun entries(): Set<Map.Entry<String, List<String>>> = headers.entries

    override fun getAll(name: String): List<String>? = headers[nameConvert(name)]

    override fun isEmpty(): Boolean = headers.isEmpty()

    override fun names(): Set<String> = headers.keys.toSet()
}