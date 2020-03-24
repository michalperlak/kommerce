package com.github.kommerce.users.domain

import org.mindrot.jbcrypt.BCrypt
import java.io.Serializable

data class Password internal constructor(
    private val hash: String
): Serializable {

    fun check(plaintext: String): Boolean =
        BCrypt.checkpw(plaintext, hash)

    companion object {
        private const val serialVersionUID: Long = 1
        private const val DEFAULT_ROUNDS = 12
        private val ROUNDS: Int =
            System.getenv("KOMMERCE_PASSWORD_GENSALT_LOG_ROUNDS")?.toIntOrNull() ?: DEFAULT_ROUNDS

        fun create(password: String): Password =
            Password(BCrypt.hashpw(password, BCrypt.gensalt(ROUNDS)))
    }
}