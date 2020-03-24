package com.github.kommerce.users

import arrow.core.Either
import arrow.core.Option
import com.github.kommerce.users.dto.NewUserDto
import com.github.kommerce.users.dto.SessionDto
import com.github.kommerce.users.dto.UserDto
import com.github.kommerce.users.error.UserCreationError
import com.github.kommerce.users.repo.SessionsRepository
import com.github.kommerce.users.repo.UsersRepository
import com.github.kommerce.users.service.UsersService

internal class DefaultUsersModule(
    private val usersRepository: UsersRepository,
    private val usersService: UsersService,
    private val sessionsRepository: SessionsRepository
) : UsersModule {

    override fun getUser(id: String): Option<UserDto> =
        usersService.getUser(id)

    override fun addUser(user: NewUserDto): Either<UserCreationError, UserDto> =
        usersService.addUser(user)

    override fun getAllUsers(): List<UserDto> = usersService.getAllUsers()

    override fun login(email: String, password: String): Option<SessionDto> =
        usersService.login(email, password)

    override fun reset() {
        usersRepository.deleteAll()
        sessionsRepository.deleteAll()
    }
}