package com.github.kommerce.users.domain

import com.github.kommerce.common.domain.Identifiable
import java.time.LocalDateTime

data class Session(
    override val id: SessionId,
    val userId: UserId,
    val expirationTime: LocalDateTime
) : Identifiable<SessionId>