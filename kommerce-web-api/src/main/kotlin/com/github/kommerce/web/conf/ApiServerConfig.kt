package com.github.kommerce.web.conf

import com.github.kommerce.common.Module
import com.github.kommerce.users.UsersModule
import com.github.kommerce.web.ApiHandler
import com.github.kommerce.web.json.JsonMapper
import com.github.kommerce.web.json.MoshiMapper
import com.github.kommerce.web.users.UsersHandler

class ApiServerConfig(
    val port: Int = DEFAULT_PORT,
    private val jsonMapper: JsonMapper = MoshiMapper()
) {
    private val usersModule = UsersModule.default()

    fun modules(): List<Module> = listOf(usersModule)

    fun handlers(): List<ApiHandler> = listOf(
        usersHandler()
    )

    private fun usersHandler(): UsersHandler = UsersHandler(usersModule, jsonMapper)

    companion object {
        const val DEFAULT_PORT = 9090
    }
}