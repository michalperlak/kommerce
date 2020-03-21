package com.github.kommerce.users

import arrow.core.Either
import arrow.core.Option
import com.github.kommerce.users.dto.NewUserDto
import com.github.kommerce.users.dto.UserDto
import com.github.kommerce.users.error.UserCreationError
import com.github.kommerce.users.repo.AiromemUsersRepo

interface UsersModule {
    fun getUser(id: String): Option<UserDto>
    fun addUser(user: NewUserDto): Either<UserCreationError, UserDto>
    fun getAllUsers(): List<UserDto>

    companion object {
        fun default(): UsersModule = DefaultUsersModule(AiromemUsersRepo())
    }
}