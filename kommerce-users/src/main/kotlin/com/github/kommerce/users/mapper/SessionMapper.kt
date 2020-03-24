package com.github.kommerce.users.mapper

import com.github.kommerce.users.domain.Session
import com.github.kommerce.users.dto.SessionDto
import java.time.ZoneOffset

fun Session.toDto(): SessionDto = SessionDto(
    token = token,
    expires = expirationTime.toEpochSecond(ZoneOffset.UTC)
)