package com.github.kommerce.users

import arrow.core.getOrHandle
import com.github.kommerce.users.dto.NewUserDto
import com.github.kommerce.users.dto.UserDto
import java.nio.file.Files

fun createUsersModule(): UsersModule = UsersModule.default(Files.createTempDirectory("users"))

fun addUser(email: String, usersModule: UsersModule): UserDto {
    val newUser = NewUserDto(email)
    return usersModule
        .addUser(newUser)
        .getOrHandle { throw IllegalStateException("Expected user but was: $it") }
}