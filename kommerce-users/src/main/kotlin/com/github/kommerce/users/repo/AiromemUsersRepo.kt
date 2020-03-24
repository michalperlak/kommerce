package com.github.kommerce.users.repo

import arrow.core.Option
import com.github.kommerce.common.repo.AiromemRepository
import com.github.kommerce.users.domain.Email
import com.github.kommerce.users.domain.User
import com.github.kommerce.users.domain.UserId
import java.nio.file.Path

class AiromemUsersRepo(path: Path) : UsersRepository {
    private val repository = AiromemRepository<UserId, User>(path)

    override fun getUser(id: UserId): Option<User> = repository.getById(id)

    override fun getByEmail(email: Email): Option<User> = repository.findOneBy(email) { it.email }

    override fun add(newUser: User): User = repository.add(newUser)

    override fun getAllUsers(): List<User> = repository.getAll()

    override fun deleteAll() = repository.deleteAll()
}