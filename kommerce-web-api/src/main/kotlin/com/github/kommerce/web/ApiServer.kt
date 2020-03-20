package com.github.kommerce.web

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

    @Synchronized
    fun stop() {
        if (serverStarted() && !disposableServer.isDisposed) {
            disposableServer.dispose()
        }
    }

    private fun serverStarted(): Boolean = ::disposableServer.isInitialized

    companion object {
        private const val DEFAULT_PORT = 9090

        fun create(port: Int = DEFAULT_PORT, vararg handlers: ApiHandler): ApiServer =
            ApiServer(
                HttpServer
                    .create()
                    .port(port)
                    .route {
                        handlers.fold(it) { routes, handler -> handler(routes) }
                    }
            )
    }
}