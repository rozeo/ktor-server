package net.roz.core.http

import io.ktor.http.*

class Request(
    override val version: String,
    override val httpMethod: HttpMethod,
    override val uri: String,
    override val schema: String,
    override val host: String,
    override val port: Int,
    override val path: String,
    override val document: String,
    override val remoteHost: String,
    override val headers: Map<String, List<String>>,
    override val queryParameters: Map<String, List<String>>,
    override val receiveParameters: Map<String, List<String>>,
): RequestInterface {
}