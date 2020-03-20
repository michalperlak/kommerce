package com.github.kommerce.web.conf

import com.github.kommerce.users.UsersModule
import com.github.kommerce.web.ApiHandler
import com.github.kommerce.web.json.JsonMapper
import com.github.kommerce.web.json.MoshiMapper
import com.github.kommerce.web.users.UsersHandler

object HandlersConfig {
    private val jsonMapper: JsonMapper = MoshiMapper()

    fun handlers(): List<ApiHandler> = listOf(
        usersHandler()
    )

    private fun usersHandler(): UsersHandler = UsersHandler(UsersModule.default(), jsonMapper)
}