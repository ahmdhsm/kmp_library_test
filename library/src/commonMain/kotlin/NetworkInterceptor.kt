package io.github.kotlin.fibonacci

import io.ktor.client.statement.HttpResponse

data class NetworkInterceptor(
    val prepare: ((Config) -> Config)? = null,
    val response: ((HttpResponse, Config) -> HttpResponse)? = null,
    val onError: ((MyAPIError) -> MyAPIError)? = null,
    val validator: ((String) -> Any) ?= null
)
