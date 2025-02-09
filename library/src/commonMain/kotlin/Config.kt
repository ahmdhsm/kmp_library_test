package io.github.kotlin.fibonacci

data class Config(
    val baseUrl: String ?= "https://api.ipify.org",
    val token: String ?= null,
    val interceptor: NetworkInterceptor ?= null
)