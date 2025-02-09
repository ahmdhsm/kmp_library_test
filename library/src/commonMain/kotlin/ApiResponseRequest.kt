package io.github.kotlin.fibonacci

import io.ktor.client.statement.HttpResponse

data class ApiResponseRequest(
    val config: Config,
    val response: HttpResponse
)
