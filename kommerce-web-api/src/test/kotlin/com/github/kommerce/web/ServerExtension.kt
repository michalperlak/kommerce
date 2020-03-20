package com.github.kommerce.web

import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext

internal class ServerExtension : BeforeAllCallback, AfterAllCallback {
    private val server: ApiServer = configureServer(TEST_PORT)

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

    companion object {
        const val TEST_PORT = 9876
    }
}