package com.github.kommerce.users

import arrow.core.Either
import arrow.core.Option
import com.github.kommerce.users.dto.NewUserDto
import com.github.kommerce.users.dto.UserDto
import com.github.kommerce.users.error.UserCreationError

interface UsersModule {
    fun getUser(id: String): Option<UserDto>
    fun addUser(user: NewUserDto): Either<UserCreationError, UserDto>
}