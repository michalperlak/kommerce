package com.github.kommerce.web

import com.github.kommerce.web.ApiServer.Companion.create
import com.github.kommerce.web.conf.ApiServerConfig
import com.github.kommerce.web.conf.ApiServerConfig.Companion.DEFAULT_PORT

fun configureServer(port: Int = DEFAULT_PORT): ApiServer =
    create(ApiServerConfig(port))

fun main() {
    configureServer()
        .startAndBlock()
}