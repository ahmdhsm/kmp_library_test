package io.github.kotlin.fibonacci

import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable

/**
 * A *Ktor Client*.
 *
 * This class has no useful logic; it's just a documentation example.
 *
 * @property httpClientEngine the name of this engine.
 * @constructor Creates an empty KtorClient.
 */
class KtorClient(httpClientEngine: HttpClientEngine, config: Config) {
    private val currentConfig = config
    private val interceptor = currentConfig.interceptor

    private val httpClient = HttpClient(httpClientEngine) {
        install(ContentNegotiation) {
            json()
        }
    }

    private suspend fun request(url: String, config: Config ?= null): ApiResponseRequest? {
        try {
            var usedConfig = config ?: currentConfig
            if (interceptor?.prepare != null) {
                usedConfig = interceptor.prepare.invoke(usedConfig)
            }

            val response = httpClient.get("${usedConfig.baseUrl}${url}")

            interceptor?.response?.invoke(response, usedConfig)

            return ApiResponseRequest(usedConfig, response)
        } catch (e: Exception) {
            val error = MyAPIError.NetworkError(e.message.toString())
            interceptor?.onError?.invoke(error)
            throw e
        }
    }

    suspend fun getIp(): IpResponse? {
        return reqGetIp()
    }

    /**
     * @return IpResponse.
     */
    suspend fun getIp(config: Config ?= null): IpResponse? {
        return reqGetIp(config)
    }

    private suspend fun reqGetIp(config: Config ?= null): IpResponse? {
        val response = request("/?format=json", config)

        val body = response?.response?.body() as String

        try {
            if (response.config.interceptor?.validator != null) {
                    val bodyString = response.config.interceptor.validator.invoke(body)

                    val json = validateAndParseJson<IpResponse>(bodyString as String)

                    val encodedJson = json as IpResponse
                    return encodedJson
            }

            val json = validateAndParseJson<IpResponse>(body)

            return json as IpResponse
        } catch (e: Exception) {
            val error = MyAPIError.ResponseMissMatch(e.message.toString())
            interceptor?.onError?.invoke(error)
            return null
        }
    }
}

@Serializable
data class IpResponse(val ip: String) {
    init {
        (ip.isNotEmpty())
    }
}