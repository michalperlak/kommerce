package com.github.kommerce.users

import arrow.core.Either
import arrow.core.Option
import arrow.core.flatMap
import com.github.kommerce.users.domain.Email
import com.github.kommerce.users.domain.User
import com.github.kommerce.users.domain.UserId
import com.github.kommerce.users.dto.NewUserDto
import com.github.kommerce.users.dto.UserDto
import com.github.kommerce.users.error.UserCreationError
import com.github.kommerce.users.mapper.toDto
import com.github.kommerce.users.repo.UsersRepository

internal class DefaultUsersModule(
    private val usersRepository: UsersRepository
) : UsersModule {

    override fun getUser(id: String): Option<UserDto> =
        UserId.from(id)
            .toOption()
            .flatMap { usersRepository.getUser(it) }
            .map { it.toDto() }

    override fun addUser(user: NewUserDto): Either<UserCreationError, UserDto> =
        Email
            .parse(user.email)
            .mapLeft { UserCreationError.EMAIL_NOT_VALID }
            .flatMap { checkIfEmailAlreadyTaken(it) }
            .map { User(id = UserId.generate(), email = it) }
            .map { usersRepository.add(it) }
            .map { it.toDto() }

    private fun checkIfEmailAlreadyTaken(email: Email): Either<UserCreationError, Email> =
        usersRepository
            .getByEmail(email)
            .toEither { email }
            .swap()
            .mapLeft { UserCreationError.EMAIL_ALREADY_USED }
}