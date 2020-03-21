package com.github.kommerce.users.domain

import arrow.core.Either
import com.github.kommerce.users.utils.of
import java.io.Serializable
import java.util.*

data class UserId(
    private val uuid: UUID
): Serializable {
    override fun toString(): String = uuid.toString()

    companion object {
        fun from(id: String): Either<Throwable, UserId> =
            Either.of {
                val uuid = UUID.fromString(id)
                UserId(uuid)
            }

        fun generate(): UserId = UserId(UUID.randomUUID())
    }
}
