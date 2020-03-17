package com.github.kommerce.users

import arrow.core.Option
import com.github.kommerce.users.domain.Email
import com.github.kommerce.users.domain.User
import com.github.kommerce.users.domain.UserId
import com.github.kommerce.users.repo.UsersRepository
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class InMemoryUsersRepository : UsersRepository {
    private val users: ConcurrentMap<UserId, User> = ConcurrentHashMap()

    override fun save(user: User): User {
        users[user.id] = user
        return user
    }

    override fun get(userId: UserId): Option<User> = Option.fromNullable(users[userId])

    override fun findByEmail(email: Email): Option<User> =
        Option.fromNullable(
            users.values.firstOrNull { it.email == email }
        )
}