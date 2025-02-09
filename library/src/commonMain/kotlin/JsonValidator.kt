package io.github.kotlin.fibonacci

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

inline fun <reified T> validateAndParseJson(jsonString: String): Any? {
    return try {
        Json.decodeFromString<T>(jsonString)
    } catch (e: SerializationException) {
        null
    }
}