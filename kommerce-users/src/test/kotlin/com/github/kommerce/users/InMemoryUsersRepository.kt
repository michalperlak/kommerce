package com.github.kommerce.users

import arrow.core.Option
import com.github.kommerce.common.repo.InMemoryRepository
import com.github.kommerce.users.domain.User
import com.github.kommerce.users.domain.UserId
import com.github.kommerce.users.repo.UsersRepository

class InMemoryUsersRepository : UsersRepository {
    private val repository = InMemoryRepository<UserId, User>()

    override fun getById(id: UserId): Option<User> = repository.getById(id)

    override fun add(value: User): User = repository.add(value)

    override fun <A : Any> findBy(value: A, extractor: (User) -> A): List<User> =
        repository.findBy(value, extractor)

    override fun <A : Any> findOneBy(value: A, extractor: (User) -> A): Option<User> =
        repository.findOneBy(value, extractor)
}
