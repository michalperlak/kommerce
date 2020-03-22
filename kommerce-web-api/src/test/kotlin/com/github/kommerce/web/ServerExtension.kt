package com.github.kommerce.web

import com.github.kommerce.web.conf.ApiServerConfig
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

internal class ServerExtension : BeforeAllCallback, AfterAllCallback, BeforeEachCallback {
    private val config: ApiServerConfig = ApiServerConfig(port = TEST_PORT)
    private val server: ApiServer = ApiServer.create(config)

    override fun beforeAll(context: ExtensionContext) {
        val serverThread = Thread {
            server
                .start()
                .onDispose()
                .block()
        }
        serverThread.start()
    }

    override fun afterAll(context: ExtensionContext) = server.stop()

    override fun beforeEach(context: ExtensionContext) {
        config
            .modules()
            .forEach { it.reset() }
    }

    companion object {
        const val TEST_PORT = 9876
    }
}