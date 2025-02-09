package io.github.kotlin.fibonacci

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

actual val httpClientEngine: HttpClientEngine = HttpClient(OkHttp).engine