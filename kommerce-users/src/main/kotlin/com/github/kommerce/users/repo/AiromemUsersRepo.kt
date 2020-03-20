package com.github.kommerce.users.repo

import arrow.core.Option
import com.github.kommerce.common.repo.AiromemRepository
import com.github.kommerce.users.domain.Email
import com.github.kommerce.users.domain.User
import com.github.kommerce.users.domain.UserId
import java.nio.file.Paths

class AiromemUsersRepo : UsersRepository {
    private val repository = AiromemRepository<UserId, User>(Paths.get("data/users"))

    override fun getUser(id: UserId): Option<User> = repository.getById(id)

    override fun getByEmail(email: Email): Option<User> = repository.findOneBy(email) { it.email }

    override fun add(newUser: User): User = repository.add(newUser)
}