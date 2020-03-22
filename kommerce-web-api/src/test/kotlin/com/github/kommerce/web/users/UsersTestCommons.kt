package com.github.kommerce.web.users

import com.github.kommerce.users.dto.NewUserDto
import com.github.kommerce.web.ServerExtension.Companion.TEST_PORT
import com.github.kommerce.web.json.MoshiMapper
import com.github.kommerce.web.users.UsersHandler.Companion.BASE_USERS_PATH
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.When

fun addUser(email: String): String {
    val newUser = NewUserDto(email = email)
    val mapper = MoshiMapper()
    val location = Given {
        port(TEST_PORT)
        body(mapper.write(newUser))
    } When {
        post(BASE_USERS_PATH)
    } Extract {
        header("location")
    }
    return location.substringAfter("$BASE_USERS_PATH/")
}