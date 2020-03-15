package com.github.kommerce.users.mapper

import com.github.kommerce.users.domain.User
import com.github.kommerce.users.dto.UserDto

fun User.toDto(): UserDto =
    UserDto(id = id.toString(), email = email.toString())