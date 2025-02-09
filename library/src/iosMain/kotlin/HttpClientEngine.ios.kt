package io.github.kotlin.fibonacci

import io.ktor.client.*
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.*

actual val httpClientEngine: HttpClientEngine = HttpClient(Darwin).engine
