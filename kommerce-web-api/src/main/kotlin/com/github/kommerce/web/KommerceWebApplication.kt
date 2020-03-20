package com.github.kommerce.web

import com.github.kommerce.web.ApiServer.Companion.DEFAULT_PORT
import com.github.kommerce.web.ApiServer.Companion.create
import com.github.kommerce.web.conf.HandlersConfig

fun configureServer(port: Int = DEFAULT_PORT): ApiServer =
    create(port, HandlersConfig.handlers())

fun main() {
    configureServer().start()
}