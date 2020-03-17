package com.github.kommerce.users

fun createUsersModule(): UsersModule = DefaultUsersModule(InMemoryUsersRepository())