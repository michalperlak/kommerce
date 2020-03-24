package com.github.kommerce.users.domain

import java.util.*

data class SessionId internal constructor(
    private val uuid: UUID
) {
    override fun toString(): String = uuid.toString()

    companion object {
        fun generate(): SessionId = SessionId(UUID.randomUUID())
    }
}