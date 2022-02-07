package net.roz.core.http

import io.ktor.http.*

interface RequestInterface {
    val version: String
    val httpMethod: HttpMethod
    val uri: String
    val schema: String
    val host: String
    val port: Int
    val path: String
    val document: String
    val remoteHost: String
    val headers: Map<String, List<String>>
    val queryParameters: Map<String, List<String>>
    val receiveParameters: Map<String, List<String>>
}