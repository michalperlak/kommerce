package com.github.kommerce.web

import com.github.kommerce.web.conf.ApiServerConfig
import reactor.netty.DisposableServer
import reactor.netty.http.server.HttpServer
import reactor.netty.http.server.HttpServerRoutes

typealias ApiHandler = (HttpServerRoutes) -> HttpServerRoutes

class ApiServer private constructor(
    private val server: HttpServer
) {
    private lateinit var disposableServer: DisposableServer

    @Synchronized
    fun start(): DisposableServer =
        if (serverStarted()) disposableServer
        else server.bindNow()

    fun startAndBlock() {
        start()
            .onDispose()
            .block()
    }

    @Synchronized
    fun stop() {
        if (serverStarted() && !disposableServer.isDisposed) {
            disposableServer.dispose()
        }
    }

    private fun serverStarted(): Boolean = ::disposableServer.isInitialized

    companion object {
        fun create(config: ApiServerConfig): ApiServer =
            ApiServer(
                HttpServer
                    .create()
                    .port(config.port)
                    .route {
                        config
                            .handlers()
                            .fold(it) { routes, handler -> handler(routes) }
                    }
            )
    }
}