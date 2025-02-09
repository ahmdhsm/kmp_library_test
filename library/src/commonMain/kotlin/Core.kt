package io.github.kotlin.fibonacci

/**
 * An *App Client*.
 *
 * This class has no useful logic; it's just a documentation example.
 *
 * @constructor Creates an empty AppClient.
 */

fun initializer(configuration: Config) : KtorClient = KtorClient(httpClientEngine, configuration)