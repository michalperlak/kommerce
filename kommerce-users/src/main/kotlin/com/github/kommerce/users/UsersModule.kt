package com.github.kommerce.users

import arrow.core.Either
import arrow.core.Option
import com.github.kommerce.common.Module
import com.github.kommerce.users.dto.NewUserDto
import com.github.kommerce.users.dto.SessionDto
import com.github.kommerce.users.dto.UserDto
import com.github.kommerce.users.error.UserCreationError
import com.github.kommerce.users.repo.AiromemUsersRepo
import com.github.kommerce.users.repo.InMemorySessionsRepo
import com.github.kommerce.users.service.UsersService
import java.nio.file.Path
import java.nio.file.Paths
import java.time.Clock

interface UsersModule : Module {
    fun getUser(id: String): Option<UserDto>
    fun addUser(user: NewUserDto): Either<UserCreationError, UserDto>
    fun getAllUsers(): List<UserDto>
    fun login(email: String, password: String): Option<SessionDto>

    companion object {
        fun default(clock: Clock = Clock.systemUTC(),
                    usersPath: Path = Paths.get("data/users")): UsersModule {
            val usersRepo = AiromemUsersRepo(usersPath)
            val sessionsRepo = InMemorySessionsRepo()
            val usersService = UsersService(clock, usersRepo, sessionsRepo)
            return DefaultUsersModule(usersRepo, usersService, sessionsRepo)
        }
    }
}