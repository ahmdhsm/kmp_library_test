package io.github.kotlin.fibonacci

import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApiClientTest {
    @Test
    fun sampleClientTest() {
        runBlocking {
            val mockEngine = MockEngine { request ->
                respond(
                    content = ByteReadChannel("""{"ip":"127.0.0.1"}"""),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }
            val ktorClient = KtorClient(mockEngine)
            val response = ktorClient.getIp()

            assertEquals("127.0.0.1", response.ip)
        }
    }
}