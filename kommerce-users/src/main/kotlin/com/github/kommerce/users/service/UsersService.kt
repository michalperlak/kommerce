package com.github.kommerce.users.service

import arrow.core.Either
import arrow.core.Option
import arrow.core.flatMap
import com.github.kommerce.users.domain.*
import com.github.kommerce.users.dto.NewUserDto
import com.github.kommerce.users.dto.SessionDto
import com.github.kommerce.users.dto.UserDto
import com.github.kommerce.users.error.UserCreationError
import com.github.kommerce.users.mapper.toDto
import com.github.kommerce.users.repo.SessionsRepository
import com.github.kommerce.users.repo.UsersRepository
import java.time.Clock

class UsersService(
    private val clock: Clock,
    private val usersRepository: UsersRepository,
    private val sessionsRepository: SessionsRepository
) {
    fun getUser(id: String): Option<UserDto> =
        UserId.from(id)
            .toOption()
            .flatMap { usersRepository.getUser(it) }
            .map { it.toDto() }

    fun addUser(user: NewUserDto): Either<UserCreationError, UserDto> =
        Email
            .parse(user.email)
            .mapLeft { UserCreationError.EMAIL_NOT_VALID }
            .flatMap { checkIfEmailAlreadyTaken(it) }
            .map { User(id = UserId.generate(), email = it, password = Password.create(user.password)) }
            .map { usersRepository.add(it) }
            .map { it.toDto() }

    fun getAllUsers(): List<UserDto> =
        usersRepository
            .getAllUsers()
            .map { it.toDto() }

    fun login(email: String, password: String): Option<SessionDto> =
        Email
            .parse(email)
            .toOption()
            .flatMap { usersRepository.getByEmail(it) }
            .filter { it.password.check(password) }
            .map { sessionsRepository.add(Session.create(it.id, clock)) }
            .map { it.toDto() }

    private fun checkIfEmailAlreadyTaken(email: Email): Either<UserCreationError, Email> =
        usersRepository
            .getByEmail(email)
            .toEither { email }
            .swap()
            .mapLeft { UserCreationError.EMAIL_ALREADY_USED }
}