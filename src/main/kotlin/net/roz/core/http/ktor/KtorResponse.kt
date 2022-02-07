package net.roz.core.http.ktor

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.roz.core.http.ResponseInterface

class KtorResponse(private val response: ResponseInterface): OutgoingContent.WriteChannelContent() {

    override val contentType: ContentType = response.contentType
    override val contentLength: Long = response.contentLength
    override val headers: Headers = response.headers
    override val status: HttpStatusCode = response.status

    private val chunked: Boolean = headers["Transfer-Encoding"].equals("chunked")
    private val chunkSize: Int = 1024 * 1024 * 5 // 5kB

    override suspend fun writeTo(channel: ByteWriteChannel) {
        if (!chunked) {
            channel.write {
                it.put(response.body.readAllBytes())
            }
        } else {
            withContext(Dispatchers.IO) {
                while(true) {
                    val readContent = response.body.readNBytes(chunkSize)
                    if (readContent.isEmpty()) {
                        break
                    }
                    channel.writeFully(readContent)
                }
            }
        }
    }
}