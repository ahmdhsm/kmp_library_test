package io.github.kotlin.fibonacci

sealed class MyAPIError {
    data class NetworkError(val message: String) : MyAPIError()
    data class ResponseMissMatch(val message: String) : MyAPIError()
}