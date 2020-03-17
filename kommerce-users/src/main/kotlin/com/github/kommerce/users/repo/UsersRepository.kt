package com.github.kommerce.users.repo

import arrow.core.Option
import com.github.kommerce.users.domain.Email
import com.github.kommerce.users.domain.User
import com.github.kommerce.users.domain.UserId

interface UsersRepository {
    fun save(user: User): User
    fun get(userId: UserId): Option<User>
    fun findByEmail(email: Email): Option<User>
}