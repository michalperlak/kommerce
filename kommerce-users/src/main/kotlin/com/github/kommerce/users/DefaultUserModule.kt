package com.github.kommerce.users

import arrow.core.Either
import arrow.core.Option
import com.github.kommerce.users.domain.UserId
import com.github.kommerce.users.dto.NewUserDto
import com.github.kommerce.users.dto.UserDto
import com.github.kommerce.users.error.UserCreationError
import com.github.kommerce.users.mapper.toDto
import com.github.kommerce.users.repo.UsersRepository

internal class DefaultUserModule(
    private val usersRepository: UsersRepository
) : UsersModule {

    override fun getUser(id: String): Option<UserDto> =
        UserId.from(id)
            .toOption()
            .flatMap { usersRepository.get(it) }
            .map { it.toDto() }

    override fun addUser(user: NewUserDto): Either<UserCreationError, UserDto> {
        TODO("Not yet implemented")
    }

}