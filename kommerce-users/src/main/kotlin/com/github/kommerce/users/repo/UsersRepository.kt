package com.github.kommerce.users.repo

import arrow.core.Option
import com.github.kommerce.users.domain.Email
import com.github.kommerce.users.domain.User
import com.github.kommerce.users.domain.UserId

interface UsersRepository {
    fun getUser(id: UserId): Option<User>
    fun getByEmail(email: Email): Option<User>
    fun add(newUser: User): User
    fun getAllUsers(): List<User>
}