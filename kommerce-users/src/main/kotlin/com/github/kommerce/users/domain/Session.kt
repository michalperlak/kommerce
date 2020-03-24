package com.github.kommerce.users.domain

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.github.kommerce.common.domain.Identifiable
import com.github.kommerce.common.time.toDate
import java.nio.charset.StandardCharsets
import java.time.Clock
import java.time.LocalDateTime

data class Session internal constructor(
    override val id: SessionId,
    val userId: UserId,
    val token: String,
    val expirationTime: LocalDateTime
) : Identifiable<SessionId> {

    companion object {
        private const val DEFAULT_SECRET = "kommerce_is_awesome"
        private val SECRET: ByteArray =
            (System.getenv("KOMMERCE_TOKEN_GENERATION_SECRET") ?: DEFAULT_SECRET)
                .toByteArray(StandardCharsets.UTF_8)

        fun create(userId: UserId, clock: Clock): Session {
            val id = SessionId.generate()
            val now = LocalDateTime.now(clock)
            val expirationTime = now.plusDays(1)
            val token = generateToken(userId, expirationTime)
            return Session(id, userId, token, expirationTime)
        }

        private fun generateToken(userId: UserId, expirationTime: LocalDateTime): String =
            JWT
                .create()
                .withIssuer("kommerce")
                .withClaim("userId", userId.toString())
                .withExpiresAt(expirationTime.toDate())
                .sign(Algorithm.HMAC256(SECRET))
    }
}