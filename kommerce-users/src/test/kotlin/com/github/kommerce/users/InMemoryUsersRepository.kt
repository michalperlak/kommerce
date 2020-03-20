package com.github.kommerce.users

import arrow.core.Option
import com.github.kommerce.common.repo.InMemoryRepository
import com.github.kommerce.users.domain.Email
import com.github.kommerce.users.domain.User
import com.github.kommerce.users.domain.UserId
import com.github.kommerce.users.repo.UsersRepository

class InMemoryUsersRepository : UsersRepository {
    private val repository = InMemoryRepository<UserId, User>()

    override fun getUser(id: UserId): Option<User> = repository.getById(id)

    override fun getByEmail(email: Email): Option<User> = repository.findOneBy(email) { it.email }

    override fun add(newUser: User): User = repository.add(newUser)
}
